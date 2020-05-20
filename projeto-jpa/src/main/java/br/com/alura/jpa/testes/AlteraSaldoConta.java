package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;

public class AlteraSaldoConta {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("alura");
		EntityManager em = emf.createEntityManager(); //gerencia entidades
		
		Conta conta = em.find(Conta.class, 2L);  //estado managed, guarda uma cópia deste objeto.
		
		em.getTransaction().begin(); //transação para alterar a conta
		conta.setSaldo(600.00); //alterando o estado do modelo, altera o estado da entidade. sincronização automática. managed
		em.getTransaction().commit(); //depois do commit, não há  sincronização automática

	}

}
