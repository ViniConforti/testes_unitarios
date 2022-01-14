package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.excecoes.FilmeSemEstoqueException;
import br.ce.wcaquino.excecoes.LocadoraException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

//Roda os testes parametrizados
@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    /* Se tiver outros parametros, eles precisam ter o value, de acordo com a ordem
     deles no array de dados( aqui é o getParametros),inicia com zero.
        Exemplo @Parameterized.Parameter(value=1)
     */
    @Parameterized.Parameter
    public List<Filme> filmes;

    @Parameterized.Parameter(value = 1)
    public String cenario;

    private LocacaoService locacaoService;

    /*Anotacao que informa ao junit que a fonte de dados vem desse método abaixo.
      Obs: O método DEVE ser estático
     */
    @Parameterized.Parameters(name="{1}")
    public static Collection<Object[]> getParametros(){
        // cada linha do array representa um cenário distinto nos testes
        return Arrays.asList(new Object[][]{
                {Arrays.asList(
                        new Filme("filme 1", 2, 5.0),
                        new Filme("filme 2", 2, 5.0),
                        new Filme("filme 3", 2, 5.0),
                        new Filme("filme 4", 2, 5.0),
                        new Filme("filme 5", 2, 5.0),
                        new Filme("filme 6", 2, 5.0)),
                        "Terceiro filme: 25%, Quarto filme: 50%, Quinto filme:75%, Sexto filme: 100%"
                }
        });
    }

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setup(){
        locacaoService = new LocacaoService();
    }

    @Test
    public void deve_calcular_valor_locacao_considerando_descontos() throws FilmeSemEstoqueException, LocadoraException {
        // cenario
        List<Filme> filmes =  Arrays.asList(
                new Filme("filme 1", 2, 5.0),
                new Filme("filme 2", 2, 5.0),
                new Filme("filme 3", 2, 5.0),
                new Filme("filme 4", 2, 5.0),
                new Filme("filme 5", 2, 5.0),
                new Filme("filme 6", 2, 5.0));

        // acao
        Locacao locacao = locacaoService.alugarFilme(new Usuario("teste"),filmes);

        //verificacao
        errorCollector.checkThat(locacao.getFilmes().get(2).getPrecoLocacao(), is(3.75));
        errorCollector.checkThat(locacao.getFilmes().get(3).getPrecoLocacao(), is(2.50));
        errorCollector.checkThat(locacao.getFilmes().get(4).getPrecoLocacao(), is(1.25));
        errorCollector.checkThat(locacao.getFilmes().get(5).getPrecoLocacao(), is(0.0));
    }
}
