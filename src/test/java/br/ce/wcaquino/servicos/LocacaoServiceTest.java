package br.ce.wcaquino.servicos;

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
    }

    /*Esse before class é executado antes da instanciacao da classe de teste.
      OBS: O método precisa ser estático, como ele vai ser executado antes da classe
      inicializar
    @BeforeClass
    public static void t(){

    }
    */
    @Test
    public void testeLocacao() throws Exception {
        // Cenario

        Usuario usuario = new Usuario("teste");
        List<Filme> filmes =  Arrays.asList(new Filme("filme 1", 2, 5.0),
                new Filme("filme 2", 2, 9.0));

        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        //resultado
        errorCollector.checkThat(locacao.getCalculaValorLocacao(), is(14.00));
        errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(),new Date()),
                    is(true));
        errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(),
                    DataUtils.obterDataComDiferencaDias(1)),is(true));
    }


    @Test(expected = FilmeSemEstoqueException.class)
    public void testeLocacao_filme_sem_estoque() throws Exception{
        // Cenario
        Usuario usuario = new Usuario("teste");

        List<Filme> filmes =  Arrays.asList(new Filme("filme 1", 2, 5.0),
                new Filme("filme 2", 0, 9.0));

        //Ação
        locacaoService.alugarFilme(usuario, filmes);
    }


    @Test()
    public void testeLocacao_usuario_vazio() throws FilmeSemEstoqueException {
        List<Filme> filmes =  Arrays.asList(new Filme("filme 1", 2, 5.0),
                new Filme("filme 2", 2, 9.0));
        try {
            locacaoService.alugarFilme(null,filmes);
            Assert.fail("Usuario nao é nulo");
        } catch (LocadoraException e) {
            MatcherAssert.assertThat(e.getMessage(),is("Usuario nulo"));
        }
    }

    @Test
    public void testeLocacao_filmes_vazio() {
        Usuario usuario = new Usuario("teste");
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
    public void testeLocacao_filme_vazio() {
        Usuario usuario = new Usuario("teste");

        List<Filme> filmes =  Arrays.asList(new Filme("filme 1", 2, 5.0), null);

        Assert.assertThrows(LocadoraException.class,
                ()-> locacaoService.alugarFilme(usuario,filmes));

        String errorMessage = Assert.assertThrows(LocadoraException.class,
                ()-> locacaoService.alugarFilme(usuario,filmes)).getMessage();

        MatcherAssert.assertThat(errorMessage, is("Filme nulo"));


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
