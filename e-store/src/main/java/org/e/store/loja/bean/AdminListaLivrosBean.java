package org.e.store.loja.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.e.store.loja.daos.LivroDao;
import org.e.store.loja.models.Livro;

@Model // substitui o Named e RequestScoped
public class AdminListaLivrosBean {

	private List<Livro> livros = new ArrayList<>();
	
	@Inject
	private LivroDao dao;
	
	@Transactional 
	public List<Livro> getLivros() {
		this.livros = dao.listar();
		return livros;		
	}
}
