package br.ce.wcaquino.servicos;


import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import junit.framework.Assert;

public class CalculadoraTest {

	static int a;
	static int b;
	static int c;
	static Calculadora calc;
	
	@BeforeClass
	public static void iniciarTestes() {
		//cenario de todos os testes
		a = 6;
		b = 3;
		c = 0;
		calc = new Calculadora();
	}
	
	@Test public void deveSomarDoisValores() {
		
		//acao
		int resultado = calc.somar(a, b);
		//verificacao
		assertEquals(9, resultado);
		
	}

	@Test public void deveSubtrairDoisValores() {
		
		//acao
		int resultado = calc.subtrair(a, b);
		//verificacao
		assertEquals(3, resultado);
		
	}
	
	@Test public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		
		//acao
		int resultado = calc.dividir(a, b);
		//verificacao
		assertEquals(2, resultado);
		
	}

	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		//acao
		int resultado = calc.dividir(a, c);
		
	}
	
	@Test
	public void deveDividir() {
		String a = "6";
		String b = "3";
		int resultado = calc.divide(a, b);
		Assert.assertEquals(2, resultado);
	}
}
