package br.ce.wcaquino.servicos.matchers;

import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

// Esse generics ali é o que vem de parametro pelo assert, nesse caso, estou esperando um Date
public class DataMatchers extends TypeSafeMatcher<Date> {
    private Integer diferencaDeDias;

    public DataMatchers(Integer diferencaDeDias){
        this.diferencaDeDias = diferencaDeDias;
    }

    @Override
    public void describeTo(Description description) {
        //Descricao do teste

        Date dataEsperada = DataUtils.obterDataComDiferencaDias(this.diferencaDeDias);
        DateFormat format = new SimpleDateFormat("dd/MM/YYYY");
        description.appendText(format.format(dataEsperada));
    }

    @Override
    protected boolean matchesSafely(Date date) {
        Date dataObtida = DataUtils.obterDataComDiferencaDias(this.diferencaDeDias);
        return DataUtils.isMesmaData(dataObtida,date);
    }
}
