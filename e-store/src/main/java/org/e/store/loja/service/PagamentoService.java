package org.e.store.loja.service;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
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
	private MailSender mailSender;
	
	@POST
	public void pagar(@Suspended final AsyncResponse ar, @QueryParam("uuid") String uuid) {
		Compra compra = compraDao.buscaPorUuid(uuid);
		
		String contextPath = context.getContextPath();
		
		executor.submit(() -> {
			try {
				
				String resposta = pagamentoGateway.pagar(compra.getTotal());
				System.out.println(resposta);
				
				URI responseURI = UriBuilder
						.fromPath("http://localhost:8080" + contextPath + "/index.xhtml")
						.queryParam("msg", "Compra realizada com sucesso")
						.build();
				
				Response response = Response.seeOther(responseURI).build();
				
				String messageBody = "Sua compra foi realizada com sucesso!";
				mailSender.send("compras@e-store.com.br", compra.getUsuario().getEmail(),
					 "Nova compra na CDC", messageBody);
				
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
