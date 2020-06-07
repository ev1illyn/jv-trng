package br.com.alura.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.alura.business.AgendamentoEmailBusiness;
import br.com.alura.entity.AgendamentoEmail;
import br.com.alura.exception.BusinessException;
import br.com.alura.interceptor.Logger;

@Path("/agendamentoemail")
public class AgendamentoEmailResource {
	
	@Inject
	private AgendamentoEmailBusiness agendamentoEmailBusinness; // injetando instância do ejb
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) // qual tipo de dado será recebido
	public Response listarAgendamentosEmail() {
		List<AgendamentoEmail> emails = agendamentoEmailBusinness.listarAgendamentosEmail();
		return Response.ok(emails).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) // qual tipo de dado será recebido
	public Response salvarAgendamentoEmail(AgendamentoEmail agendamentoEmail) throws BusinessException{
		agendamentoEmailBusinness.salvarAgendamentoEmail(agendamentoEmail);
		return Response.status(201).build();
		
	}
}
