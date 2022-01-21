package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

public class CalculadoraMockTest {

    @Mock
    private Calculadora calculadora;

    @Mock
    private  Calculadora calcMock;

    @Spy
    private Calculadora calculadoraSpy;

    @Spy
    private EmailService emailService;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void devo_mostrar_diferenca_entre_mock_e_spy(){
        Mockito.when(calcMock.soma(2,2)).thenReturn(5);

        /* O mock que nao sabe o que fazer caso os valores passados por parametros
           sejam diferente da expectativa, ele retorna o valor padrao do método, que nesse caso é int,
           entao é zero.

           O Spy, quando os valores por parametros sao diferentes dos da expectativa, ele faz a execucao
           real do metodo. Caso os parametros sejam os esperados pela expectativa, ele retorna o valor
           da expectativa.

           OBS: Spy NAO FUNCIONADA COM INTERFACES?(TALVEZ UM DIA NAO FUNCIONASSE), justamente por ele fazer a execucao
            do método real,
           quando os valores por parametros sao diferentes dos da expectativa
         */

       // Mockito.when(calculadoraSpy.soma(1,1)).thenReturn(8);

        /*Quando for usar expectativa pro spy, melhor usar da forma abaixo, assim nao tem risco
        do método real ser executado
        Mockito.doReturn(5).when(calculadoraSpy).soma(1,3);
        */
        Mockito.doReturn(5).when(calculadoraSpy).soma(1,3);
        Mockito.doNothing().when(calculadoraSpy).imprimir();

        System.out.println("Mock "+calcMock.soma(2,2));
        System.out.println("Spy "+calculadoraSpy.soma(1,3));

        System.out.println("Mock");

        // Quando o método é void, o mock nao faz nada
        calcMock.imprimir();

        System.out.println("Spy");
        calculadoraSpy.imprimir();
    }

    @Test
    public void test(){
        //Calculadora calculadora = Mockito.mock(Calculadora.class);
        /* O calculadora soma nao ira somar de fato os valores, neste caso,
        se o método calculadora.soma(1,2) for invocado, o mockito realmente irá retornar 5.

        Caso o método calculadora.soma seja invocado com valores diferentes, ele irá retornar zero
        (zero pq o tipo de retorno da funcao é um inteiro e o valor default pro inteiro é zero), pois
        a expectativa que foi gravada para ele é que calculadora.soma(1,2) retorne 5.
         */

        //Mockito.when(calculadora.soma(1,2)).thenReturn(5);

        /* Quando se utiliza um matcher para um parametros, TODOS os outros parametros precisam
        utilizar um matcher tbm
         */

        /* O ArgumentCaptor serve para capturar os valores passados por parametro na funcao que sera testada,
           ele pode servir como um matcher igual no caso abaixo do calculadora.soma
         */

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.when(calculadora.soma(argumentCaptor.capture(),argumentCaptor.capture())).thenReturn(5);
        assertThat(calculadora.soma(1,10), is(5));
        //System.out.println(argumentCaptor.getAllValues());
    }
}
