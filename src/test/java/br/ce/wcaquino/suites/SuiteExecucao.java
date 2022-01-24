package br.ce.wcaquino.suites;

import br.ce.wcaquino.servicos.CalculadoraTest;
import br.ce.wcaquino.servicos.CalculoValorLocacaoTest;
import br.ce.wcaquino.servicos.LocacaoServiceTest;
import org.junit.runners.Suite;

//Nessa anotacao, é informado que a classe vai executar como uma suite de testes
//@RunWith(Suite.class)

/* Nessa anotacao é especificada as classes de testes que serão executadas
 OBS: Prefiro criar um profile de teste no maven pra esse tipo de execucao.*/

@Suite.SuiteClasses({
        CalculadoraTest.class,
        CalculoValorLocacaoTest.class,
        LocacaoServiceTest.class
})
public class SuiteExecucao {

    /*
    @BeforeClass
    public static void before(){
        System.out.println("before each class battery test");
    }

    @AfterClass
    public static void after(){
        System.out.println("after each class battery test");
    }
    */

}
