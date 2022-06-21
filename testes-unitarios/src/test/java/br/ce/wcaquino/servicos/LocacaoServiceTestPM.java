package br.ce.wcaquino.servicos;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.wcaquino.builders.LocacaoBuilder.umLocacao;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.MatchersProprios.*;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterData;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocacaoService.class, DataUtils.class})
public class LocacaoServiceTestPM {

	@InjectMocks
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
		service = PowerMockito.spy(service);
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

		//PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(obterData(17, 6, 2022));
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 17);
		calendar.set(Calendar.MONTH, Calendar.JUNE);
		calendar.set(Calendar.YEAR, 2022);
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);

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

	@Test
	public void naoDeveDevolverFilmeNoDomingo() throws Exception {

		//Assume.assumeTrue(verificarDiaSemana(new Date(), Calendar.SATURDAY));

		//cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(umFilme().agora());

		// powerMockito
		//whenNew(Date.class).withNoArguments().thenReturn(obterData(18, 6, 2022));
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 18);
		calendar.set(Calendar.MONTH, Calendar.JUNE);
		calendar.set(Calendar.YEAR, 2022);
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);

		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);

		//verificacao

		// 1ª boolean ehSegunda = verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
		// 1ª Assert.assertTrue(ehSegunda);

		// 2ª assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));

		// 3ª assertThat(retorno.getDataRetorno(), MatchersProprios.caiEm(Calendar.SUNDAY));
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());

		Calendar.getInstance();

	}

	@Test
	public void deveAlugarFilme_SemCalcularValor() throws Exception {

		//cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		PowerMockito.doReturn(1.0).when(service, "calcularValorLocacao", filmes);

		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		//verificacao
		Assert.assertThat(locacao.getValor(), is(1.0));
		PowerMockito.verifyPrivate(service).invoke("calcularValorLocacao", filmes);
	}


	@Test
	public void deveCalcularValorLocacao() throws Exception {
		//cenario
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		//acao
		Double valor = (Double) Whitebox.invokeMethod(service, "calcularValorLocacao", filmes);
		//verificacao
		Assert.assertThat(valor, is(4.0));
	}
}
	//cenario
	//acao
	//verificacao