package br.com.alura.maven;

public class Produto {

	private final String nome;
	private final double preco;
	private final double numero;
	
	public Produto(String nome, double preco) {
		super();
		this.numero = 0;
		this.nome = nome;
		this.preco = preco;
	}

	public double getNumero() {
		return numero;
	}

	public String getNome() {
		return nome;
	}

	public double getPreco() {
		return preco;
	}
	
	public double getPrecoComImposto() {
		return preco * 1.1;
	}
	
}
