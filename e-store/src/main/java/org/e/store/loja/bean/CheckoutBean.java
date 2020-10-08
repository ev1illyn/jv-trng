package org.e.store.loja.bean;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.e.store.loja.models.CarrinhoCompras;
import org.e.store.loja.models.Compra;
import org.e.store.loja.models.Usuario;

@Model
public class CheckoutBean {

	
	@Inject
	private CarrinhoCompras carrinho;
	
	/*
	 * finaliza a compra e faz uma requisição a aplicação como se fosse um serviço
	 * externo
	 */
	@Transactional
	public void finalizar() {
		Compra compra = new Compra();
		compra.setUsuario(usuario);
		carrinho.finalizar(compra);
		
		String contextName = facesContext.getExternalContext().getRequestContextPath();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
		response.setHeader("Location", contextName + "/" + "service/pagamento?uuid=" + compra.getUuid());
		
	}
	
}
