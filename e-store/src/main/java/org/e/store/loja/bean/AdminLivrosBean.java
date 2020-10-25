package org.e.store.loja.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import org.e.store.loja.daos.AutorDao;
import org.e.store.loja.daos.LivroDao;
import org.e.store.loja.infra.FileSaver;
import org.e.store.loja.models.Autor;
import org.e.store.loja.models.Livro;
import org.e.store.loja.security.CurrentUser;

@Model
public class AdminLivrosBean {

	private Livro livro = new Livro();

	// CDI cuida dessas injeções de dependências
	@Inject
	private LivroDao dao;

	@Inject
	private AutorDao autorDao;
	
	@Inject
	private FacesContext context;
	
	// Objeto que facilita upload de arquivos, uma opção para não ter que trabalhar com bytes
	private Part capaLivro;

	private List<Integer> autoresId = new ArrayList<>();
	
	// gerenciada pelo JTA. TransactionRequiredException, caso não coloque a annotation
	@Transactional 
	public String salvar() throws IOException {
		
		dao.salvar(livro);
		FileSaver fileSaver = new FileSaver();
		livro.setCapaPath(fileSaver.write(capaLivro, "livros"));
		
		 // Flash Scope - guarda o valor na sessão e dura apenas de um request pra outro
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Livro cadastrado com sucesso!"));

		return "admin/livros/lista?faces-redirect=true";
	}

	public List<Autor> getAutores() {
		return autorDao.listar();
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Part getCapaLivro() {
		return capaLivro;
	}

	public void setCapaLivro(Part capaLivro) {
		this.capaLivro = capaLivro;
	}
}
