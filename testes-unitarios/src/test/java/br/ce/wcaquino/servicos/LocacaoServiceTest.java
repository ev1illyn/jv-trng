package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.wcaquino.builders.LocacaoBuilder.umLocacao;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.MatchersProprios.caiNumaSegunda;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHoje;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static br.ce.wcaquino.utils.DataUtils.*;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.runners.ParallelRunner;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import buildermaster.BuilderMaster;

@RunWith(ParallelRunner.class)
public class LocacaoServiceTest {

	@InjectMocks @Spy
	private LocacaoService service;

	@Mock
	private SPCService spc;

	@Mock
	private EmailService email;

	@Mock
	LocacaoDAO dao;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() {
		service = new LocacaoService();
	}

	@Test
	public void deveAlugarFilme_ComEstoque() throws Exception {

		// cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filme = asList(umFilme().agora());

		Mockito.doReturn(obterData(17, 6, 2022)).when(service).obterData();

		// acao
		Locacao locacao = service.alugarFilme(usuario, filme);

		// verificacao + matchers + Error collector(faz todas as assertivas mesmo que algum teste falhe)

		error.checkThat(locacao.getValor(), is(equalTo(4.0)));
		error.checkThat(locacao.getValor(), is(not(4.1)));

		error.checkThat(isMesmaData(locacao.getDataLocacao(),
				obterData(17, 6, 2022)), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(),
				obterData(18, 6, 2022)), is(true));

	}

	// forma elegante, melhor exemplo - apenas a exceção importa
	@Test(expected = FilmeSemEstoqueException.class)
	public void deveLancarExcecao_filmeSemEstoque() throws Exception {

		// cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filme = asList(umFilmeSemEstoque().agora());

		// acao
		service.alugarFilme(usuario, filme);

	}

	// forma robusta, mais indicada - messagem importa
	@Test
	public void deveLancarExcecao_filmeSemEstoque_2() {

		// cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filme = asList(umFilme().semEstoque().agora());

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
		Usuario usuario = umUsuario().agora();
		List<Filme> filme = asList(umFilme().semEstoque().agora());

		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque.");

		// acao
		service.alugarFilme(usuario, filme);

	}

	@Test
	public void deveLancarExcecao_usuarioVazio() throws
			FilmeSemEstoqueException {

		// cenario
		List<Filme> filme = asList(umFilme().comValor(8.0).agora());
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
		Usuario usuario = umUsuario().agora();

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio.");

		service.alugarFilme(usuario, null);

	}

	@Test
	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException,
			LocadoraException {
		//cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(
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
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(
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
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(
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
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(
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
	public void naoDeveDevolverFilmeNoDomingo() throws Exception {

		//cenario
		Usuario usuario = umUsuario().agora();

		List<Filme> filmes = asList(umFilme().agora());
		Mockito.doReturn(obterData(18, 6, 2022)).when(service).obterData();

		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);

		//verificacao
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
	}

	@Test
	public void naoDeveAlugarFilmeParaNegativadoSPC() throws Exception {
		//cenario
		Usuario usuario = umUsuario().agora();
		Usuario usuario2 = umUsuario().comNome("Usuário 2").agora();
		List<Filme> filmes = asList(umFilme().agora());

		try {
			Mockito.when(spc.possuiNegativacao(any(Usuario.class))).thenReturn(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

//		exception.expect(LocadoraException.class);
//		exception.expectMessage("Usuário negativado.");

		//acao
		try {
			service.alugarFilme(usuario, filmes);
			//verificacao
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuário negativado."));
		}

		verify(spc).possuiNegativacao(usuario);

	}

	@Test
	public void deveEnviarEmailParaLocacoesAtrasadas() {
		//cenario
		Usuario usuario = umUsuario().agora();
		Usuario usuario2 = umUsuario().comNome("Emilly").agora();
		Usuario usuario3 = umUsuario().comNome("Loki").agora();
		List<Locacao> locacoes = asList(
				umLocacao()
						.atrasada()
						.comUsuario(usuario)
						.agora(),
				umLocacao()
						.comUsuario(usuario2)
						.agora(),
				umLocacao()
						.atrasada()
						.comUsuario(usuario3)
						.agora(),
				umLocacao()
						.atrasada()
						.comUsuario(usuario3)
						.agora());

		Mockito.when(dao.obterLocacoesPendentes()).thenReturn(locacoes);

		//acao
		service.notificarAtrasos();

		//verificacao
		verify(email, times(3))
				.notificarAtraso(any(Usuario.class));
		verify(email).notificarAtraso(usuario);
		verify(email, times(2)).notificarAtraso(usuario3);
		verify(email, never()).notificarAtraso(usuario2);
		Mockito.verifyNoMoreInteractions(email);
		Mockito.verifyZeroInteractions(spc);
	}

	@Test
	public void deveTratarErroNoSPC() throws Exception {
		//cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());

		Mockito.when(spc.possuiNegativacao(usuario)).thenThrow(new Exception("Problemas com SPC, tente novamente."));

		//verificacao, essa expectativa fica antes da acao pois depois dela a exceção será lançada e não haverá resposta
		exception.expect(LocadoraException.class);
		exception.expectMessage("Problemas com SPC, tente novamente.");

		//acao
		service.alugarFilme(usuario, filmes);
	}

	@Test
	public void deveProrrogarUmaLocacao() {
		//cenario
		Locacao locacao = umLocacao().agora();

		//acao
		service.prorrogarLocacao(locacao, 3);

		//verificacao
		ArgumentCaptor<Locacao> argumentoCapturado = ArgumentCaptor.forClass(Locacao.class);
		verify(dao).salvar(argumentoCapturado.capture());
		Locacao locacaoRetornada = argumentoCapturado.getValue();

		//errorColletor pega todos os erros e printa no console
		error.checkThat(locacaoRetornada.getValor(), is(12.0));
		error.checkThat(locacaoRetornada.getDataLocacao(), ehHoje());
		error.checkThat(locacaoRetornada.getDataRetorno(), ehHojeComDiferencaDias(3));
	}

	@Test
	public void deveCalcularValorLocacao() throws Exception {
		//cenario
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		//acao
		Class<LocacaoService> classe = LocacaoService.class;
		Method metodo = classe.getDeclaredMethod("calcularValorLocacao",
				List.class);
		metodo.setAccessible(true);
		Double valor = (Double) metodo.invoke(service, filmes);
		//verificacao
		Assert.assertThat(valor, is(4.0));
	}
}

//cenario
//acao
//verificacao
