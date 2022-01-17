package br.ce.wcaquino.servicos.matchers;

import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DiaSemanaMatcher extends TypeSafeMatcher<Date> {

    private int diaSemana;
    public DiaSemanaMatcher(int diaSemana){
        this.diaSemana = diaSemana;
    }

    @Override
    protected boolean matchesSafely(Date date) {
        return DataUtils.verificarDiaSemana(date,this.diaSemana);
    }

    @Override
    public void describeTo(Description description) {
        //Descricao do teste
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,this.diaSemana);
        String dataExtenso = calendar.getDisplayName(Calendar.DAY_OF_WEEK,
                Calendar.LONG, new Locale("pt","BR"));

        description.appendText(dataExtenso);
    }
}
