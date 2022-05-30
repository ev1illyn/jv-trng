package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

	private LocacaoService service;
	
	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value=1)
	public Double valorLocacao;
	
	@Parameter(value=2)
	public String cenario;
	
	@Before
	public void setup() {
		service = new LocacaoService();
	}
	
	private static Filme interestelar = new Filme("Interestelar", 1, 4.0);
	private static Filme shrek = new Filme("Shrek", 1, 4.0);
	private static Filme jogosVorazes = new Filme("Jogos Vorazes", 1, 4.0);
	private static Filme narnia = new Filme("Nárnia", 1, 4.0);
	private static Filme divergente = new Filme("Divergente", 1, 4.0);
	private static Filme harryPotter = new Filme("Harry Potter", 1, 4.0 );
	private static Filme naruto = new Filme("Naruto", 1, 4.0 );
	
	@Parameters(name="Teste {2}")
	public static Collection<Object[]> getParametros(){
		return Arrays.asList(new Object[][] {

			{Arrays.asList(interestelar, shrek), 8.0, "2º filme: sem desconto"},
			{Arrays.asList(interestelar, shrek, jogosVorazes), 11.0, "3º filme: 25%"},
			{Arrays.asList(interestelar, shrek, jogosVorazes, narnia), 13.0, "4º filme: 50%"},
			{Arrays.asList(interestelar, shrek, jogosVorazes, narnia, divergente), 14.0, "5º filme: 75%"},
			{Arrays.asList(interestelar, shrek, jogosVorazes, narnia, divergente, harryPotter), 14.0, "6º filme: 100%"},
			{Arrays.asList(interestelar, shrek, jogosVorazes, narnia, divergente, harryPotter, naruto), 18.0, "7º filme: Sem desconto"}
			
		});
	}
	
	@Test //teste genérico
	public void deveCalcularValorLocacaoComDesconto() throws FilmeSemEstoqueException,
		LocadoraException {
		
		//cenario
		Usuario usuario = new Usuario("Emilly");
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao 14.0
		assertThat(resultado.getValor(), is(valorLocacao));		
	}
	
	@Test
	public void print() {
		System.out.println(valorLocacao);
	}
	
}
