package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean //classe gerenciada pelo jsf
@ViewScoped
public class LivroBean implements Serializable{

	private Livro livro = new Livro();
	private Integer autorId;
	
	public Livro getLivro() {
		return livro;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public List<Autor> getAutoresDoLivro() {
		for (Autor a : this.livro.getAutores()) {
			System.out.println(a.getNome());
		}
		return this.livro.getAutores();
	}

	public void setAutoresDoLivro(List<Autor> autores) {
		this.livro.setAutores(autores);
	}
	
	public List<Livro> getLivros() {
		return new DAO<Livro>(Livro.class).listaTodos();
	}
	
	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}
	
	public RedirectView formAutor() {
		return new RedirectView("autor?faces-redirect=true");
	}
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
		}
	}
	
	public void gravar() {

		if (livro.getAutores().isEmpty()) {
			/* throw new RuntimeException("Livro deve ter pelo menos um Autor."); */
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelos menos um autor"));
			return;
		}
		
		if (this.livro.getId() == null) {
			new DAO<Livro>(Livro.class).adiciona(this.livro);
		} else {
			new DAO<Livro>(Livro.class).atualiza(this.livro);
		}
		
		this.livro = new Livro();
	}
	
	public void editar(Livro livro) {
		this.livro = livro;
	}
	
	public void remover(Livro livro) {
		new DAO<Livro>(Livro.class).remove(livro);
	}
	
	public void removerAutorDoLivro(Autor autor) {
	    this.livro.removeAutor(autor);
	}
	
}
