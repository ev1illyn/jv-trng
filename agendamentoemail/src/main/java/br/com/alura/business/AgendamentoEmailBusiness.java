package br.com.alura.business;

import java.util.*;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.alura.dao.AgendamentoEmailDao;
import br.com.alura.entity.AgendamentoEmail;

@Stateless // classe de negócio EJB, gerenciada pelo container do java ee
public class AgendamentoEmailBusiness {

	@Inject
	private AgendamentoEmailDao agendamentoEmailDao;
	
	public List<AgendamentoEmail> listarAgendamentosEmail() { // retorna uma lista de emails
		return agendamentoEmailDao.listarAgendamentoEmail();
	}
	
	public void salvarAgendamentoEmail(AgendamentoEmail agendamentoEmail) {
		agendamentoEmail.setEnviado(false);
		agendamentoEmailDao.salvarAgendamentoEmail(agendamentoEmail);
	}
}
