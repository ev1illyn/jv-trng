package org.e.store.loja.bean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.e.store.loja.daos.LivroDao;
import org.e.store.loja.models.Livro;

@Named
@RequestScoped
public class AdminLivrosBean {

	private Livro livro = new Livro();
	
	@Inject // CDI cuida dessas injeções de dependências
	private LivroDao dao;
	
	@Transactional // TransactionRequiredException, caso não coloque a annotation
	public void salvar() {
		dao.salvar(livro);
		System.out.println("Livro salvo!" + livro);
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
}
