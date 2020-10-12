package org.e.store.loja.service;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.e.store.loja.daos.CompraDao;
import org.e.store.loja.infra.MailSender;
import org.e.store.loja.models.Compra;

@Path("/pagamento")
public class PagamentoService {

	@Context
	ServletContext context;
	
	@Inject
	private CompraDao compraDao;
	
	@Inject
	private PagamentoGateway pagamentoGateway;
	
	private static ExecutorService executor = Executors.newFixedThreadPool(50);
	
	@Inject
	private JMSContext jmsContext; // especificação JAVA EE, para enviar e-mail assíncronos. Se comunica com o servidor e cria mensagem

	@Resource(name="java:/jms/topics/CarrinhoComprasTopico")
	private Destination destination;
	
	/* O AsyncResponse é assíncrono no servidos apenas para novos usuários*/
	@POST
	public void pagar(@Suspended final AsyncResponse ar, @QueryParam("uuid") String uuid) {
		Compra compra = compraDao.buscaPorUuid(uuid);
		
		String contextPath = context.getContextPath();
		
		JMSProducer producer = jmsContext.createProducer();
		
		executor.submit(() -> {
			try {
				
				String resposta = pagamentoGateway.pagar(compra.getTotal());
				System.out.println(resposta);
				
				URI responseURI = UriBuilder
						.fromPath("http://localhost:8080" + contextPath + "/index.xhtml")
						.queryParam("msg", "Compra realizada com sucesso")
						.build();
				
				Response response = Response.seeOther(responseURI).build();
				
				producer.send(destination, compra.getUuid()); // envio da mensagem para EnviaEmailCompra
				
				ar.resume(response);
				
			} catch (Exception e) {
				ar.resume(new WebApplicationException(e));
			}
		});
		
	}

	public CompraDao getCompraDao() {
		return compraDao;
	}

	public void setCompraDao(CompraDao compraDao) {
		this.compraDao = compraDao;
	}

	public PagamentoGateway getPagamentoGateway() {
		return pagamentoGateway;
	}

	public void setPagamentoGateway(PagamentoGateway pagamentoGateway) {
		this.pagamentoGateway = pagamentoGateway;
	}
}
