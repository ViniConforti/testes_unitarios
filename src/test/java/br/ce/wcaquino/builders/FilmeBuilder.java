package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Filme;

public class FilmeBuilder {
    private Filme filme;

    private FilmeBuilder(){

    }

    // Somente o método de entrada precisa ser estatico
    public static FilmeBuilder umFilme(){
        FilmeBuilder filmeBuilder = new FilmeBuilder();
        filmeBuilder.filme = new Filme();
        filmeBuilder.filme.setNome("Filme 1");
        filmeBuilder.filme.setEstoque(2);
        filmeBuilder.filme.setPrecoLocacao(5.0);
        return filmeBuilder;
    }

    public FilmeBuilder semEstoque(){
      filme.setEstoque(0);
      return this;
    }

    public Filme agora(){
        return this.filme;
    }
}
