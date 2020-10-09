package org.e.store.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArrayBuilder;

import org.e.store.loja.daos.CompraDao;
import org.e.store.loja.service.PagamentoGateway;

@SessionScoped
@Named
public class CarrinhoCompras implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Set<CarrinhoItem> itens = new HashSet<>();

	@Inject
	private CompraDao compraDao;
	
	@Inject
	private PagamentoGateway pagamentoGateway;
	
	public void add(CarrinhoItem item) {
		itens.add(item);
	}
	
	public List<CarrinhoItem> getItens() {
		return new ArrayList<CarrinhoItem>(itens);
	}
	
	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		for (CarrinhoItem carrinhoItem: itens) {
			total = total.add(
					carrinhoItem.getLivro().getPreco().multiply(
							new BigDecimal(carrinhoItem.getQuantidade())));
		}
		return total;
	}
	
	public BigDecimal getTotal(CarrinhoItem item) {
	    return item.getLivro().getPreco().multiply(
	            new BigDecimal(item.getQuantidade()));
	}
	
	// stream() do Java 8
	public Integer getQuantidadeTotal() {
		return itens.stream().mapToInt(item -> item.getQuantidade()).sum();
	}
	
	public void finalizar(Compra compra) {
		compra.setItens(toJson());
		compra.setTotal(getTotal());
		compraDao.salvar(compra);
		
	}
	
	private String toJson() {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (CarrinhoItem item : itens) {
			builder.add(Json.createObjectBuilder()
				.add("titulo", item.getLivro().getTitulo())
				.add("preco", item.getLivro().getPreco())
				.add("quantidade", item.getQuantidade())
				.add("total", getTotal(item)) ); 
		}
		 
		return builder.build().toString();
	}
	
}
