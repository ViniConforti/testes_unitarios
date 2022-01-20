package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.junit.Test;
import org.mockito.Mockito;

public class CalculadoraMockTest {

    @Test
    public void test(){
        Calculadora calculadora = Mockito.mock(Calculadora.class);
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

        Mockito.when(calculadora.soma(Mockito.eq(1),Mockito.anyInt())).thenReturn(5);
        assertThat(calculadora.soma(1,10), is(5));
    }
}
