package br.ce.wcaquino.servicos;

import br.ce.wcaquino.excecoes.DivisaoPorZero;

public class Calculadora {
    public int soma(int a, int b){
      return a+b;
    }


    public int subtracao(int a, int b) {
        return a-b;
    }

    public int multiplicacao(int a, int b) {
        return a*b;
    }

    public int divisao(int a, int b) throws DivisaoPorZero {
        if (a == 0 || b == 0)
            throw new DivisaoPorZero();
        return a/b;

    }

    public void imprimir(){
        System.out.println("Passei aqui");
    }
}
