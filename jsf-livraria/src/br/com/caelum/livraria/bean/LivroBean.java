package br.com.caelum.livraria.bean;

import javax.faces.bean.ManagedBean;

@ManagedBean() //classe gerenciada pelo jsf
public class LivroBean {

	private Livro livro = new Livro();
	
	public Livro getLivro() {
		return livro;
	}

	public void gravar() {
		System.out.println("gravando livro... " + this.livro.getTitulo());
	}
	
}
