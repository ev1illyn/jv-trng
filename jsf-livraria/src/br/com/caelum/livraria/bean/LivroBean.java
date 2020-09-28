package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModel;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped
public class LivroBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Livro livro = new Livro();

	private Integer autorId;

	private Integer livroId;
	
	private List<Livro> livros;
	
	@Inject
	private LivroDataModel livroDataModel;
	
	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");
	
	@Inject
	private LivroDao livroDao;
	
	@Inject
	private AutorDao autorDao;
	
	public LivroBean(LivroDao livroDao) {
		this.livroDao = livroDao;
	}

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public Livro getLivro() {
		return livro;
	}
	
	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}
	
	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void setAutoresDoLivro(List<Autor> autores) {
		this.livro.setAutores(autores);
	}
	

	public List<Livro> getLivros() {
		if (this.livros == null) {
			this.livros = livroDao.listaTodos();
		}
		return livros;
	}
	
	public void gravarAutor() {
		Autor autor = autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}
	
	public RedirectView formAutor() { return new
		RedirectView("autor?faces-redirect=true");
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
			livroDao.adiciona(this.livro);
			
			this.livros = livroDao.listaTodos();
		} else {
			livroDao.atualiza(this.livro);
		}
		
		this.livro = new Livro();
	}
	
	public void editar(Livro livro) {
		this.livro = livro;
	}
	
	public void remover(Livro livro) {
		livroDao.remove(livro);
	}
	
	public void removerAutorDoLivro(Autor autor) {
	    this.livro.removeAutor(autor);
	}
	
	public void buscarLivroPorId() {
        this.livro = livroDao.buscaPorId(this.livroId);
    }
	
	public boolean precoEMenor(Object valorColuna, Object filtroDigitado, Locale locale) {
		
		String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();
		
		if (filtroDigitado == null || textoDigitado.equals("")) {
			return true;
		}
		if (valorColuna == null) {
			return false;
		}
		
		try {
			Double precoDigitado = Double.valueOf(textoDigitado);
			Double precoColuna = (Double) valorColuna;
			
			return precoColuna.compareTo(precoDigitado) < 0;
		} catch (NumberFormatException e) {
			return false;
		}
		
	}

	public LivroDataModel getLivroDataModel() {
		return livroDataModel;
	}

	public void setLivroDataModel(LivroDataModel livroDataModel) {
		this.livroDataModel = livroDataModel;
	}

	public List<String> getGeneros() {
		return generos;
	}
	
}
