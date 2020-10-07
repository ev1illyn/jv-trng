package org.e.store.loja.bean;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.e.store.loja.daos.LivroDao;
import org.e.store.loja.models.CarrinhoCompras;
import org.e.store.loja.models.CarrinhoItem;
import org.e.store.loja.models.Livro;

@Model
public class CarrinhoComprasBean {

	@Inject
	private LivroDao livroDao;
	
	@Inject
	private CarrinhoCompras carrinho;
	
	public String add(Integer id) {
		Livro livro = livroDao.buscarPorId(id);
		CarrinhoItem item = new CarrinhoItem(livro);
		carrinho.add(item);
		
		return "carrinho?faces-redirect=true";
	}
	
	public List<CarrinhoItem> getItens() {
		return carrinho.getItens();
	}
	
}
