package br.ce.wcaquino.excecoes;

public class FilmeSemEstoqueException extends Exception {
    public FilmeSemEstoqueException(String message){
        super(message);
    }
}
