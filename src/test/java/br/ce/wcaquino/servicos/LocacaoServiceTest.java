package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.UsuarioBuilder.*;
import static br.ce.wcaquino.builders.FilmeBuilder.*;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.daos.LocacaoDaoFake;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.excecoes.FilmeSemEstoqueException;
import br.ce.wcaquino.excecoes.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.MatcherAssert;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.mockito.Mockito;

import static br.ce.wcaquino.servicos.matchers.MatchersProprios.*;

import java.util.*;

public class LocacaoServiceTest {

    /* anotacao interessante, caso um teste tenha varios asserts
       os outros asserts serao verificados, mesmo que um deles falhe
    * */
    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    private LocacaoService locacaoService;

    /*
    Se precisar de alguma variavel que seja persistida entre um teste e outro,
    utilize ela como estatica, assim o junit nao vai reinicializar ela a cada teste,
    caso vc a utilize no @Before
    private static int count;

    */

    /* Esse before é executado antes de cada método de teste.
       Tudo que for executado aqui é reinicializado a cada teste
     */
    @Before
    public void setup(){
        locacaoService = new LocacaoService();
        LocacaoDAO locacaoDAO = Mockito.mock(LocacaoDAO.class);
        locacaoService.setLocacaoDAO(locacaoDAO);
    }

    /*Esse before class é executado antes da instanciacao da classe de teste.
      OBS: O método precisa ser estático, como ele vai ser executado antes da classe
      inicializar
    @BeforeClass
    public static void t(){

    }
    */
    @Test
    public void deve_Efetuar_Locacao_Com_Sucesso() throws Exception {
        // Cenario
        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(),Calendar.SATURDAY));

        Usuario usuario = umUsuario().agora();
        List<Filme> filmes =  Arrays.asList(umFilme().agora(), umFilme().agora());

        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        //resultado
        errorCollector.checkThat(locacao.getCalculaValorLocacao(), is(10.00));
        errorCollector.checkThat(locacao.getDataLocacao(),ehHoje());
        errorCollector.checkThat(locacao.getDataRetorno(),ehHojeComDiferenciaDeDias(1));
    }


    @Test(expected = FilmeSemEstoqueException.class)
    public void deve_Lancar_Excecao_Caso_Filme_Nao_Tenha_Estoque() throws Exception{
        // Cenario
        Usuario usuario = umUsuario().agora();;

        List<Filme> filmes =  Arrays.asList(umFilme().semEstoque().agora(),
                umFilme().semEstoque().agora());

        //Ação
        locacaoService.alugarFilme(usuario, filmes);
    }


    @Test()
    public void deve_Lancar_Excecao_Caso_Usuario_Seja_Nulo() throws FilmeSemEstoqueException {
        List<Filme> filmes =  Arrays.asList(umFilme().agora(), umFilme().agora());
        try {
            locacaoService.alugarFilme(null,filmes);
            Assert.fail("Usuario nao é nulo");
        } catch (LocadoraException e) {
            MatcherAssert.assertThat(e.getMessage(),is("Usuario nulo"));
        }
    }

    @Test
    public void deve_Lancar_Excecao_Caso_Filme_Seja_Nulo() {
        Usuario usuario = umUsuario().agora();;
        Assert.assertThrows(LocadoraException.class,
                ()-> locacaoService.alugarFilme(usuario,null));

        String errorMessage = Assert.assertThrows(LocadoraException.class,
                ()-> locacaoService.alugarFilme(usuario,null)).getMessage();

        MatcherAssert.assertThat(errorMessage, is("Filmes nulos"));

        List<Filme> filmes = Collections.emptyList();

        Assert.assertThrows(LocadoraException.class,
                ()-> locacaoService.alugarFilme(usuario,filmes));

        errorMessage = Assert.assertThrows(LocadoraException.class,
                ()-> locacaoService.alugarFilme(usuario,filmes)).getMessage();

        MatcherAssert.assertThat(errorMessage, is("Filmes nulos"));

    }

    @Test
    public void deve_Lancar_Excecao_Caso_A_Lista_De_Filmes_Esteja_Vazia_Ou_Nula() {
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes =  Arrays.asList(umFilme().agora(), null);

        Assert.assertThrows(LocadoraException.class,
                ()-> locacaoService.alugarFilme(usuario,filmes));

        String errorMessage = Assert.assertThrows(LocadoraException.class,
                ()-> locacaoService.alugarFilme(usuario,filmes)).getMessage();

        MatcherAssert.assertThat(errorMessage, is("Filme nulo"));


    }


    @Test
    public void deve_Aplicar_Descontos_Na_Locacao_Nos_Filmes_3_25pct_4_50pct_5_75pct_e_6_100pct() throws FilmeSemEstoqueException, LocadoraException {
        // cenario
        List<Filme> filmes =  Arrays.asList(
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora());

        // acao
        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(),filmes);

        //verificacao
        errorCollector.checkThat(locacao.getFilmes().get(2).getPrecoLocacao(), is(3.75));
        errorCollector.checkThat(locacao.getFilmes().get(3).getPrecoLocacao(), is(2.50));
        errorCollector.checkThat(locacao.getFilmes().get(4).getPrecoLocacao(), is(1.25));
        errorCollector.checkThat(locacao.getFilmes().get(5).getPrecoLocacao(), is(0.0));


    }



    @Test
    //@Ignore // Nao executa esse teste, ele é ignorado
    public void nao_deve_devolver_locacao_no_domingo() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        /*Assumption, assume que a data de hj é sabado, nesse caso de teste
         Se nao for, o teste sera ignorado
         */
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(),Calendar.SATURDAY));

        List<Filme> filmes =  Arrays.asList(
                umFilme().agora(),
                umFilme().agora());

        // acao
        Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(),filmes);

        //verificacao
        MatcherAssert.assertThat(locacao.getDataRetorno(), caiEm(Calendar.MONDAY));
        MatcherAssert.assertThat(locacao.getDataRetorno(), caiNaSegunda());
    }

    /*
    @Test
    public void testeLocacao_filme_sem_estoque_2(){
        // Cenario
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("teste");
        Filme filme = new Filme("filme 1",0,5.0);

        //Ação
        try {
            locacaoService.alugarFilme(usuario, filme);
            Assert.fail("Filme com estoque");
        } catch (Exception e) {
            assertThat(e.getMessage(), is("Filme sem estoque"));
        }
    }

    // Forma mais atual
    @Test
    public void testeLocacao_filme_sem_estoque_3() {
        // Cenario
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("teste");
        Filme filme = new Filme("filme 1",0,5.0);

        //Ação
        errorCollector.checkThrows(FilmeSemEstoqueException.class,
                ()-> locacaoService.alugarFilme(usuario, filme));
    }
     */
}
