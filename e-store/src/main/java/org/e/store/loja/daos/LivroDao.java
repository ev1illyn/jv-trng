package org.e.store.loja.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.e.store.loja.models.Livro;

public class LivroDao {
	
	@PersistenceContext()
	private EntityManager manager;

	public void salvar(Livro livro) {
		manager.persist(livro);
	}
	
}
