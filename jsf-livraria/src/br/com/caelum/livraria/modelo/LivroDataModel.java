package br.com.caelum.livraria.modelo;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.model.SortOrder;

import org.primefaces.model.LazyDataModel;
import br.com.caelum.livraria.dao.LivroDao;

public class LivroDataModel extends LazyDataModel<Livro> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	LivroDao livroDao;
		
	@PostConstruct
	void init() {
		super.setRowCount(livroDao.contaTodos());	
	}
	
	public List<Livro> load(int inicio, int quantidade, String campoOrdenacao,
			SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
		// filtros = genero=Romance
		// titulo = null;
		String titulo = (String) filtros.get("titulo");
		return livroDao.listaTodosPaginada(inicio, quantidade, "titulo", titulo);
	}
	
}