package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static br.ce.wcaquino.utils.DataUtils.verificarDiaSemana;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		service = new LocacaoService();
	}

	@After
	public void tearDown() {
		service = new LocacaoService();
	}
	
	@Test
	public void deveAlugarFilme_ComEstoque() throws Exception {

		assumeFalse(verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cenario
		Usuario usuario = new Usuario("Evillyn");
		List<Filme> filme = Arrays.asList(new Filme("Interestelar", 2, 10.0));

		// acao
		Locacao locacao;

		locacao = service.alugarFilme(usuario, filme);

		// verificacao
		Assert.assertTrue(locacao.getDataLocacao().toLocaleString(), true);
		Assert.assertTrue(locacao.getDataRetorno().toLocaleString(), true);
		Assert.assertTrue(locacao.getValor() == 10.0);

		// verificacao
		Assert.assertTrue(locacao.getDataLocacao().toLocaleString(), true);
		Assert.assertTrue(locacao.getDataRetorno().toLocaleString(), true);
		Assert.assertEquals(10.0, locacao.getValor(), 0.01);

		// verificacao + fluent interface
		assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
		assertThat(locacao.getValor(), is(equalTo(10.0)));
		assertThat(locacao.getValor(), is(not(10.1)));

		// verificacao + Error collector(faz todas as assertivas mesmo que algum teste
		// falhe)
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
		error.checkThat(locacao.getValor(), is(equalTo(10.0)));
		error.checkThat(locacao.getValor(), is(not(10.1)));

	}

	// forma elegante, melhor exemplo - apenas a exceção importa
	@Test(expected = FilmeSemEstoqueException.class)
	public void deveLancarExcecao_filmeSemEstoque() throws Exception {

		// cenario
		Usuario usuario = new Usuario("Evillyn");
		List<Filme> filme = Arrays.asList(new Filme("Interestelar", 0, 10.0));

		// acao
		service.alugarFilme(usuario, filme);

	}

	// forma robusta, mais indicada - messagem importa
	@Test
	public void deveLancarExcecao_filmeSemEstoque_2() {

		// cenario
		Usuario usuario = new Usuario("Evillyn");
		List<Filme> filme = Arrays.asList(new Filme("Interestelar", 0, 10.0));

		// acao
		try {
			service.alugarFilme(usuario, filme);
			Assert.fail("Deveria ter lançado uma exceção");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme sem estoque."));
		}

	}

	// forma nova, messagem importa
	@Test
	public void deveLancarExcecao_filmeSemEstoque_3() throws Exception {

		// cenario
		Usuario usuario = new Usuario("Evillyn");
		List<Filme> filme = Arrays.asList(new Filme("Interestelar", 0, 10.0));

		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque.");

		// acao
		service.alugarFilme(usuario, filme);

	}

	@Test
	public void deveLancarExcecao_usuarioVazio() throws
		FilmeSemEstoqueException {

		// cenario
		List<Filme> filme = Arrays.asList(new Filme("Interestelar", 1, 10.0));

		// acao
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuário vazio."));
		}
		
	}

	@Test
	public void deveLancarExcecao_filmeVazio()
			throws FilmeSemEstoqueException, LocadoraException {

		// cenario
		Usuario usuario = new Usuario("Evillyn");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio.");
		
		service.alugarFilme(usuario, null);
		
	}
	
	@Test
	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException,
		LocadoraException {
		//cenario
		Usuario usuario = new Usuario("Emilly");
		List<Filme> filmes = Arrays.asList(
				new Filme("Interestelar", 1, 4.0),
				new Filme("Shrek", 1, 4.0),
				new Filme("Harry Potter", 1, 4.0));
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		//verificacao
		assertThat(resultado.getValor(), is(11.0));		
	}
	
	@Test
	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException,
		LocadoraException {
		//cenario
		Usuario usuario = new Usuario("Emilly");
		List<Filme> filmes = Arrays.asList(
				new Filme("Interestelar", 1, 4.0),
				new Filme("Shrek", 1, 4.0),
				new Filme("Jogos Vorazes", 1, 4.0),
				new Filme("Harry Potter", 1, 4.0));
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		//verificacao
		assertThat(resultado.getValor(), is(13.0));		
	}
	
	@Test
	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException,
		LocadoraException {
		//cenario
		Usuario usuario = new Usuario("Emilly");
		List<Filme> filmes = Arrays.asList(
				new Filme("Interestelar", 1, 4.0),
				new Filme("Shrek", 1, 4.0),
				new Filme("Jogos Vorazes", 1, 4.0),
				new Filme("Nárnia", 1, 4.0),
				new Filme("Harry Potter", 1, 4.0));
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		//verificacao
		assertThat(resultado.getValor(), is(14.0));		
	}
	
	@Test
	public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException,
		LocadoraException {
		//cenario
		Usuario usuario = new Usuario("Emilly");
		List<Filme> filmes = Arrays.asList(
				new Filme("Interestelar", 1, 4.0),
				new Filme("Shrek", 1, 4.0),
				new Filme("Jogos Vorazes", 1, 4.0),
				new Filme("Nárnia", 1, 4.0),
				new Filme("Divergente", 1, 4.0),
				new Filme("Harry Potter", 1, 4.0));
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		//verificacao
		assertThat(resultado.getValor(), is(14.0));		
	}
	
	@Test
	public void naoDeveDevolverFilmeNoDomingo() throws
		FilmeSemEstoqueException, LocadoraException {
		assumeTrue(verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenario
		Usuario usuario = new Usuario("Loki");
		List<Filme> filmes = Arrays.asList(
				new Filme("Harry Potter", 1, 4.0));
		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		//verificacao
		boolean ehSegunda = DataUtils.
				verificarDiaSemana(retorno.getDataRetorno(),
						Calendar.MONDAY);
		Assert.assertTrue(ehSegunda);
	}
}
