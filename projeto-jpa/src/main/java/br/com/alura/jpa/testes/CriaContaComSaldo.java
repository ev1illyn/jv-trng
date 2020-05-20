package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;

public class CriaContaComSaldo {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("alura");
		EntityManager em = emf.createEntityManager(); //gerencia entidades
		
		Conta conta = new Conta();  //cria objeto conta e seta valores
		conta.setTitular("Hugo");
		conta.setAgencia(2023);
		conta.setNumero(18054);
		conta.setSaldo(1000.00);

		em.getTransaction().begin(); //transação para persistir objeto
		em.persist(conta);
		em.getTransaction().commit(); //depois do commit, não há  sincronização automática
		em.close();
		
		EntityManager em2 = emf.createEntityManager();
		System.out.println("id da conta do huguinho: " + conta.getId());
		conta.setSaldo(1000.01);
		em2.getTransaction().begin();
		em2.merge(conta); // select, depois update. detached
		em2.getTransaction().commit();
		em.close();
		

	}

}
