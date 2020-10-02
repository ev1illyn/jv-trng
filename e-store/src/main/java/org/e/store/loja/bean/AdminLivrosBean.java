package org.e.store.loja.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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

	// CDI cuida dessas injeções de dependências
	@Inject
	private LivroDao dao;

	@Inject
	private AutorDao autorDao;
	
	@Inject
	private FacesContext context;

	private List<Integer> autoresId = new ArrayList<>();
	
	// gerenciada pelo JTA. TransactionRequiredException, caso não coloque a annotation
	@Transactional 
	public String salvar() {
		
		for (Integer autorId : autoresId) {
			livro.getAutores().add(new Autor(autorId));
		}
		
		dao.salvar(livro);
		
		 // Flash Scope - guarda o valor na sessão e dura apenas de um request pra outro
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Livro cadastrado com sucesso!"));

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
