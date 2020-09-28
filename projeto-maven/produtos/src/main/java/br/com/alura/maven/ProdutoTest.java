package br.com.alura.maven;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ProdutoTest {

	@Test
	public void verificaPrecoComImposto() {
		Produto livro = new Produto("Java, como programar!", 0.10);
		assertEquals(0.11, livro.getPrecoComImposto(), 0.00001);
	}
}
