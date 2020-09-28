package br.com.caelum.livraria.modelo;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import org.primefaces.model.LazyDataModel;

import br.com.caelum.livraria.dao.DAO;

public class LivroDataModel extends LazyDataModel<Livro> {
	
	private DAO<Livro> dao = new DAO<Livro>(Livro.class);
	
	public LivroDataModel() {
		super.setRowCount(dao.quantidadeDeElementos());	
	}
	
	public List<Livro> load(int inicio, int quantidade, String campoOrdenacao,
			SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
		// filtros = genero=Romance
		// titulo = null;
		String titulo = (String) filtros.get("titulo");
		return dao.listaTodosPaginada(inicio, quantidade, "titulo", titulo);
	}
	
}