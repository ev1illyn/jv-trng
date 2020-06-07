package br.com.alura.business;

import java.util.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;

import br.com.alura.dao.AgendamentoEmailDao;
import br.com.alura.entity.AgendamentoEmail;
import br.com.alura.exception.BusinessException;
import br.com.alura.interceptor.Logger;

@Stateless // classe de negócio EJB, gerenciada pelo container do java ee
@Logger // qualificador
public class AgendamentoEmailBusiness {

	@Inject
	private AgendamentoEmailDao agendamentoEmailDao;
	
	public List<AgendamentoEmail> listarAgendamentosEmail() { // retorna uma lista de emails
		return agendamentoEmailDao.listarAgendamentoEmail();
	}
	
	public void salvarAgendamentoEmail(@Valid AgendamentoEmail agendamentoEmail) throws BusinessException {
		
		if (!agendamentoEmailDao
				.listarAgendamentosEmailPorEmail(agendamentoEmail.getEmail())
				.isEmpty()) {
			throw new BusinessException("Que feio servidor! Esse e-mail já está agendado!");
		}
		
		agendamentoEmail.setEnviado(false);
		agendamentoEmailDao.salvarAgendamentoEmail(agendamentoEmail);
	}
}
