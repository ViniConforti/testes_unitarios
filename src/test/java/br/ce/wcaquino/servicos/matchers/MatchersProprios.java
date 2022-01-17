package br.ce.wcaquino.servicos.matchers;

import java.util.Calendar;

public class MatchersProprios {
    public static DiaSemanaMatcher caiEm(int diaSemana){
        return new DiaSemanaMatcher(diaSemana);
    }

    public static DiaSemanaMatcher caiNaSegunda(){
        return new DiaSemanaMatcher(Calendar.MONDAY);
    }

    public static DataMatchers ehHoje(){
        return new DataMatchers(0);
    }

    public static DataMatchers ehHojeComDiferenciaDeDias(Integer diferancaDeDias){
        return new DataMatchers(diferancaDeDias);
    }
}
