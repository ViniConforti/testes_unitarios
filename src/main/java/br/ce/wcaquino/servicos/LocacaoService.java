package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.excecoes.FilmeSemEstoqueException;
import br.ce.wcaquino.excecoes.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {
	private LocacaoDAO locacaoDAO;
	private SPCService spcService;
	private EmailService emailService;

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {

		if (filmes == null || filmes.isEmpty())
			throw new LocadoraException("Filmes nulos");

		for (Filme filme: filmes) {

			if (filme == null)
				throw new LocadoraException("Filme nulo");

			if (filme.getEstoque() == 0)
				throw new FilmeSemEstoqueException("Filme sem estoque");

		}

		if(usuario == null)
			throw new LocadoraException("Usuario nulo");

		boolean negativado;

		try {
			negativado = spcService.possuiNegativacao(usuario);
		} catch (Exception e) {
			throw new LocadoraException("Problemas com SPC, tente novamente");
		}

		if (negativado)
			throw new LocadoraException("Usuario negativado");

		Locacao locacao = new Locacao();
		locacao.setFilmes(this.aplicar_descontos(filmes));
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setCalculaValorLocacao(filmes);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY))
			dataEntrega = adicionarDias(dataEntrega,1);
		locacao.setDataRetorno(dataEntrega);

		//Salvando a locacao...
		//TODO adicionar método para salvar
		locacaoDAO.salvar(locacao);

		return locacao;
	}

	public List<Filme> aplicar_descontos(List<Filme> filmes){
		if (filmes.size() >=3) {
			Double valorFilme3 = filmes.get(2).getPrecoLocacao() -
					((filmes.get(2).getPrecoLocacao() / 100) * 25);
			filmes.get(2).setPrecoLocacao(valorFilme3);
		}

		if (filmes.size() >=4) {
			Double valorFilme4 = filmes.get(3).getPrecoLocacao() -
					(filmes.get(3).getPrecoLocacao() / 100) * 50;
			filmes.get(3).setPrecoLocacao(valorFilme4);
		}

		if (filmes.size() >=5) {
			Double valorFilme5 = filmes.get(4).getPrecoLocacao() -
					(filmes.get(4).getPrecoLocacao() / 100) * 75;
			filmes.get(4).setPrecoLocacao(valorFilme5);
		}

		if(filmes.size() >=6) {
			Double valorFilme6 = filmes.get(5).getPrecoLocacao() -
					(filmes.get(5).getPrecoLocacao() / 100) * 100;
			filmes.get(5).setPrecoLocacao(valorFilme6);
		}

		return filmes;

	}

	public void notificarAtrasos(){
		List<Locacao> locacoes = locacaoDAO.obterLocacoesPendentes();
		for(Locacao locacao: locacoes) {
			if (locacao.getDataRetorno().before(new Date()))
				emailService.notificarAtraso(locacao.getUsuario());
		}
	}
}