package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped
public class AutorBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Autor autor = new Autor();
	
	private Integer autorId;
	
	@Inject
	private AutorDao autorDao;
	
	public AutorBean(AutorDao dao) {
		this.autorDao = dao;
	}

	public Autor getAutor() {
		return autor;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public RedirectView gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if (this.autor.getId() == null) {
			this.autorDao.adiciona(this.autor);
		} else {
			this.autorDao.atualiza(this.autor);
		}
		
		this.autor = new Autor();
		
		return new RedirectView("livro?faces-redirect=true");
	}
	
	public List<Autor> getAutores(){
		return this.autorDao.listaTodos();
	}
	
	public void editar(Autor autor) {
		this.autor = autor;
	}
	
	public void remover(Autor autor){
		this.autorDao.remove(autor);
	}
	
	public void buscarAutorPorId() {
		this.autor = this.autorDao.buscaPorId(autorId);
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

}