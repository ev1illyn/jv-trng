package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;

public class CriaConta {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("alura");
		EntityManager em = emf.createEntityManager(); //gerencia entidades
		
		Conta conta = new Conta();  //cria objeto conta e seta valores
		conta.setTitular("Emilly");
		conta.setAgencia(2021);
		conta.setNumero(1806);

		em.getTransaction().begin(); //transação para persistir objeto
		em.persist(conta);
		em.getTransaction().commit(); //depois do commit, não há  sincronização automática
		
	}

}
