package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import br.ce.wcaquino.excecoes.DivisaoPorZero;
import br.ce.wcaquino.runners.ParallelRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

//@RunWith(ParallelRunner.class)
public class CalculadoraTest {
    private Calculadora calculadora;

    @Before
    public void setup(){
        calculadora = new Calculadora();
    }

    @Test
    public void somarDoisValores(){
        //cenario

        int a = 5;
        int b = 2;

        // acao
        int resultado  = calculadora.soma(a,b);

        //verificacao
        assertThat(resultado, is(7));
    }

    @Test
    public void subtrairDoisValores(){
        // cenario
        int a = 9;
        int b = 2;

        //acao
        int resultado = calculadora.subtracao(a,b);

        //verifcacao
        assertThat(resultado, is(7));
    }

    @Test
    public void multiplicarDoisValores(){
        //cenario
        int a = 5;
        int b = 4;

        //acao
        int resultado = calculadora.multiplicacao(a,b);

        //verificacao
        assertThat(resultado, is(20));
    }

    @Test
    public void dividirDoisValores() throws DivisaoPorZero{
        //cenario
        int a = 9;
        int b = 3;

        //acao
        int resultado = calculadora.divisao(a,b);

        //verificacao
        assertThat(resultado, is(3));
    }

    @Test
    public void testar_divisao_por_zero(){
        int a = 0;
        int b = 10;
        Assert.assertThrows(DivisaoPorZero.class, ()-> calculadora.divisao(a,b));
    }
}
