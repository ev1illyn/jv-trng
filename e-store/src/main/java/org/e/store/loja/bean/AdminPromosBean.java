package org.e.store.loja.bean;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.e.store.loja.models.Promo;
import org.e.store.loja.websockets.PromosEndpoint;

@Model
public class AdminPromosBean {
	
	private Promo promo = new Promo();
	
	@Inject
	private PromosEndpoint promosEndpoint;
	
	public void enviar() {
		promosEndpoint.send(promo);
	}

	public Promo getPromo() {
		return promo;
	}

	public void setPromo(Promo promo) {
		this.promo = promo;
	}
	
}
