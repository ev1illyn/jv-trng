package org.e.store.loja.bean;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.e.store.loja.daos.LivroDao;
import org.e.store.loja.models.Livro;

@Model
public class HomeBean {

	@Inject
	private LivroDao livroDao;
	
	public List<Livro> ultimosLancamentos() {
		return livroDao.ultimosLancamentos();
	}
	
	public List<Livro> demaisLivros() {
		return livroDao.demaisLivros();
		
	}
	
}
