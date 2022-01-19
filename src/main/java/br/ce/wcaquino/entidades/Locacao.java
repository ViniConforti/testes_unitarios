package br.ce.wcaquino.entidades;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Locacao {

	private Usuario usuario;
	private List<Filme> filmes;
	private Date dataLocacao;
	private Date dataRetorno;
	private Double valor = 0D;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getDataLocacao() {
		return dataLocacao;
	}
	public void setDataLocacao(Date dataLocacao) {
		this.dataLocacao = dataLocacao;
	}
	public Date getDataRetorno() {
		return dataRetorno;
	}
	public void setDataRetorno(Date dataRetorno) {
		this.dataRetorno = dataRetorno;
	}
	public Double getCalculaValorLocacao() {
		return valor;
	}
	public void setCalculaValorLocacao(List<Filme> filmes) {
		for (Filme filme: filmes)
			valor += filme.getPrecoLocacao();

	}
	public List<Filme> getFilmes() {
		return filmes;
	}
	public void setFilmes(List<Filme> filme) {
		this.filmes = filme;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Locacao locacao = (Locacao) o;
		return Objects.equals(usuario, locacao.usuario) && Objects.equals(filmes,
				locacao.filmes) && Objects.equals(dataLocacao, locacao.dataLocacao) && Objects.equals(
				dataRetorno, locacao.dataRetorno) && Objects.equals(valor, locacao.valor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(usuario, filmes, dataLocacao, dataRetorno, valor);
	}
}