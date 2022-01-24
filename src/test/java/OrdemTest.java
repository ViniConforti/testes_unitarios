import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

// Anotação que garante a ordem de execução dos testes. Aqui esta sendo em ordem ascendente
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemTest {

    public static int count = 0;

    //@Test
    @Before
    public void init(){
        count++;
    }

    @Test
    public void verify(){
        MatcherAssert.assertThat(count, CoreMatchers.is(1));
    }

}
