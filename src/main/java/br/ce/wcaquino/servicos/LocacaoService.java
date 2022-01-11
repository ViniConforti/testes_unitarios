package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Date;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.excecoes.FilmeSemEstoqueException;
import br.ce.wcaquino.excecoes.LocadoraException;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws FilmeSemEstoqueException, LocadoraException {

		if(filme == null)
			throw new LocadoraException("Filme nulo");

		if (filme.getEstoque() == 0)
			throw new FilmeSemEstoqueException("Filme sem estoque");

		if(usuario == null)
			throw new LocadoraException("Usuario nulo");


		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);

		//Salvando a locacao...
		//TODO adicionar método para salvar

		return locacao;
	}
}