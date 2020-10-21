package org.e.store.loja.bean;

import javax.enterprise.inject.Model;

import org.e.store.loja.models.Promo;

@Model
public class AdminPromosBean {

	private Promo promo = new Promo();
	
	public void enviar() {
		System.out.println(promo.getTitulo());
		System.out.println(promo.getLivro());
	}

	public Promo getPromo() {
		return promo;
	}

	public void setPromo(Promo promo) {
		this.promo = promo;
	}
	
}
