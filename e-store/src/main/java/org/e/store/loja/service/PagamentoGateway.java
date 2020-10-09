package org.e.store.loja.service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import org.e.store.loja.daos.CompraDao;
import org.e.store.loja.models.Pagamento;

public class PagamentoGateway implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CompraDao compraDao;
	
	public String pagar(BigDecimal total) {
		
		Client client = ClientBuilder.newClient();
		Pagamento pagamento = new Pagamento(total);		
		String target = "http://book-payment.herokuapp.com/payment";
		Entity<Pagamento> json = Entity.json(pagamento);
		return client.target(target).request().post(json, String.class);
	}

	public CompraDao getCompraDao() {
		return compraDao;
	}

	public void setCompraDao(CompraDao compraDao) {
		this.compraDao = compraDao;
	}
}
