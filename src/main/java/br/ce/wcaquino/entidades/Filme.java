package br.ce.wcaquino.entidades;

import java.util.Objects;

public class Filme {

	private String nome;
	private Integer estoque;
	private Double precoLocacao;  
	
	public Filme() {}
	
	public Filme(String nome, Integer estoque, Double precoLocacao) {
		this.nome = nome;
		this.estoque = estoque;
		this.precoLocacao = precoLocacao;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getEstoque() {
		return estoque;
	}
	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
	public Double getPrecoLocacao() {
		return precoLocacao;
	}
	public void setPrecoLocacao(Double precoLocacao) {
		this.precoLocacao = precoLocacao;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Filme filme = (Filme) o;
		return Objects.equals(nome, filme.nome) && Objects.equals(estoque,
				filme.estoque) && Objects.equals(precoLocacao, filme.precoLocacao);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome, estoque, precoLocacao);
	}
}