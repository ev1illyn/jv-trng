package org.e.store.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.e.store.loja.models.Autor;

public class AutorDao {

	@PersistenceContext
	private EntityManager manager;
	
	public List<Autor> listar() {
		return manager.createQuery("select a from Autor a", Autor.class).getResultList();
	}

}
