package br.ce.wcaquino.servicos;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.daos.LocacaoDaoFake;
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
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static br.ce.wcaquino.builders.FilmeBuilder.*;
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
                        umFilme().agora(),
                        umFilme().agora(),
                        umFilme().agora(),
                        umFilme().agora(),
                        umFilme().agora(),
                        umFilme().agora()),
                        "Terceiro filme: 25%, Quarto filme: 50%, Quinto filme:75%, Sexto filme: 100%"
                }
        });
    }

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setup(){
        locacaoService = new LocacaoService();
        LocacaoDAO locacaoDAO = Mockito.mock(LocacaoDAO.class);
        locacaoService.setLocacaoDAO(locacaoDAO);

        SPCService spcService = Mockito.mock(SPCService.class);
        locacaoService.setSpcService(spcService);

        EmailService emailService = Mockito.mock(EmailService.class);
        locacaoService.setEmailService(emailService);
    }

    @Test
    public void deve_calcular_valor_locacao_considerando_descontos() throws FilmeSemEstoqueException, LocadoraException {
        // cenario
        List<Filme> filmes =  Arrays.asList(
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora(),
                umFilme().agora());

        // acao
        Locacao locacao = locacaoService.alugarFilme(new Usuario("teste"),filmes);

        //verificacao
        errorCollector.checkThat(locacao.getFilmes().get(2).getPrecoLocacao(), is(3.75));
        errorCollector.checkThat(locacao.getFilmes().get(3).getPrecoLocacao(), is(2.50));
        errorCollector.checkThat(locacao.getFilmes().get(4).getPrecoLocacao(), is(1.25));
        errorCollector.checkThat(locacao.getFilmes().get(5).getPrecoLocacao(), is(0.0));
    }
}
