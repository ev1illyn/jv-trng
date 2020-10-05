package org.e.store.loja.bean;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.e.store.loja.daos.LivroDao;
import org.e.store.loja.models.Livro;

@Model
public class LivroDetalheBean {

	@Inject
	private LivroDao livroDao;
	private Livro livro;
	private Integer id;
	
	@Transactional
	public void carregarDetalhe() {
		this.livro = livroDao.buscarPorId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
}
