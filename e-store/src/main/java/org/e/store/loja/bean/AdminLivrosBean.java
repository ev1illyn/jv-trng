package org.e.store.loja.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.e.store.loja.daos.AutorDao;
import org.e.store.loja.daos.LivroDao;
import org.e.store.loja.models.Autor;
import org.e.store.loja.models.Livro;

@Named
@RequestScoped
public class AdminLivrosBean {

	private Livro livro = new Livro();
	
	@Inject // CDI cuida dessas injeções de dependências
	private LivroDao dao;
	
	@Inject
	private AutorDao autorDao;
	
	private List<Integer> autoresId = new ArrayList<>();
	
	@Transactional // gerenciada pelo JTA. TransactionRequiredException, caso não coloque a annotation
	public String salvar() {
		for (Integer autorId : autoresId) {
			livro.getAutores().add(new Autor(autorId));
		}
		System.out.println("Autores!" + autoresId);
		
		dao.salvar(livro);
		System.out.println("Livro salvo!" + livro);
		this.livro = new Livro();
		this.autoresId = new ArrayList<>();
		
		return "/livros/lista?faces-redirect=true";
	}

	public List<Autor> getAutores() {
		return autorDao.listar();
	}
	
	public List<Integer> getAutoresId() {
		return autoresId;
	}

	public void setAutoresId(List<Integer> autoresId) {
		this.autoresId = autoresId;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
}
