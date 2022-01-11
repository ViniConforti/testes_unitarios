package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.excecoes.FilmeSemEstoqueException;
import br.ce.wcaquino.excecoes.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.Date;

public class LocacaoServiceTest {

    /* anotacao interessante, caso um teste tenha varios asserts
       os outros asserts serao verificados, mesmo que um deles falhe
    * */
    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void testeLocacao() throws Exception {
        // Cenario
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("teste");
        Filme filme = new Filme("filme 1",2,5.0);

        //Ação

        Locacao locacao = locacaoService.alugarFilme(usuario, filme);

        //resultado
        errorCollector.checkThat(locacao.getValor(), is(5.00));
        errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(),new Date()),
                    is(true));
        errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(),
                    DataUtils.obterDataComDiferencaDias(1)),is(true));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void testeLocacao_filme_sem_estoque() throws Exception{
        // Cenario
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("teste");
        Filme filme = new Filme("filme 1",0,5.0);

        //Ação
        locacaoService.alugarFilme(usuario, filme);
    }

    @Test()
    public void testeLocacao_usuario_vazio() throws FilmeSemEstoqueException {
        LocacaoService locacaoService = new LocacaoService();
        Filme filme = new Filme("filme 1",2,5.0);
        try {
            locacaoService.alugarFilme(null,filme);
            Assert.fail("Usuario nao é nulo");
        } catch (LocadoraException e) {
            MatcherAssert.assertThat(e.getMessage(),is("Usuario nulo"));
        }
    }

    @Test
    public void testeLocacao_filme_vazio() {
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("teste");
        Assert.assertThrows(LocadoraException.class,
                ()-> locacaoService.alugarFilme(usuario,null));

        String errorMessage = Assert.assertThrows(LocadoraException.class,
                ()-> locacaoService.alugarFilme(usuario,null)).getMessage();

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
