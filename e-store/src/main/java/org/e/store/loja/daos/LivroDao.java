package org.e.store.loja.daos;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.e.store.loja.models.Livro;
import org.hibernate.jpa.QueryHints;

@Stateful
public class LivroDao {
	
	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager manager;

	public void salvar(Livro livro) {
		manager.persist(livro);
	}

	public List<Livro> listar() {
		String jpql = "select distinct(l) from Livro l "
				+ " join fetch l.autores";
		return manager.createQuery(jpql, Livro.class)
				.getResultList();
	}

	public List<Livro> ultimosLancamentos() {
		String jpql = "select l from Livro l order by l.id desc";
		return manager.createQuery(jpql, Livro.class)
				.setMaxResults(5)
				.setHint(QueryHints.HINT_CACHEABLE, true)
				.getResultList();
	}
	
	public List<Livro> demaisLivros() {
		String jpql = "select l from Livro l order by l.id";
		return manager.createQuery(jpql, Livro.class)
				.setFirstResult(5)
				.setHint(QueryHints.HINT_CACHEABLE, true)
				.getResultList();
	}
	
	public Livro buscarPorId(Integer id) {
		return manager.find(Livro.class, id);
	}
	
}
