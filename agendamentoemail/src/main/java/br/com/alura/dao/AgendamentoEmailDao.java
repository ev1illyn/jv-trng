package br.com.alura.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.alura.entity.AgendamentoEmail;

@Stateless
public class AgendamentoEmailDao {
	
	@PersistenceContext //cria um contexto de persistência
	private EntityManager entityManager;
	
	public List<AgendamentoEmail> listarAgendamentoEmail(){
		return entityManager.createQuery("select agendamentos from AgendamentoEmail agendamentos"
				, AgendamentoEmail.class).getResultList();
	}
	
	public AgendamentoEmail alterarAgendamentoEmail(AgendamentoEmail agendamentoEmail) {
		return entityManager.merge(agendamentoEmail);
	}
	
	public void salvarAgendamentoEmail(AgendamentoEmail agendamentoEmail) {
		entityManager.persist(agendamentoEmail);
	}
	
	public List<AgendamentoEmail> listarAgendamentosEmailPorEmail(String email) {
		
		Query query = entityManager.createQuery("select a from AgendamentoEmail a where a.email =:email and a.enviado = false ", AgendamentoEmail.class);
		query.setParameter("email", email);
		
		return query.getResultList();
		
	}

	public List<AgendamentoEmail> listarAgendamentosEmailNaoEnviados() {
		return entityManager.createQuery("select agendamentos from AgendamentoEmail agendamentos where a.enviador = false", AgendamentoEmail.class).getResultList();
	}
	
}
