package org.e.store.loja.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/pagamento")
public class PagamentoService {

	@POST
	public Response pagar(@QueryParam("uuid") String uuid) {
		return null;
	}
}
