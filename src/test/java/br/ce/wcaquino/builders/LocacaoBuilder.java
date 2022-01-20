package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;

public class LocacaoBuilder {
    private Locacao locacao;

    private LocacaoBuilder(){

    }

    // Somente o método de entrada precisa ser estatico
    public static LocacaoBuilder umaLocacao(){
        LocacaoBuilder locacaoBuilder = new LocacaoBuilder();
        locacaoBuilder.locacao = new Locacao();

        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilme().agora(), umFilme().agora());

        locacaoBuilder.locacao.setFilmes(filmes);
        locacaoBuilder.locacao.setUsuario(usuario);
        locacaoBuilder.locacao.setDataLocacao(new Date());
        locacaoBuilder.locacao.setCalculaValorLocacao(filmes);
        locacaoBuilder.locacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(2));

        return locacaoBuilder;
    }

    public LocacaoBuilder emAtraso(){
        locacao.setDataLocacao(DataUtils.obterDataComDiferencaDias(-3));
        locacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(-1));
        return this;
    }

    public LocacaoBuilder comUsuario(Usuario usuario){
        locacao.setUsuario(usuario);
        return this;
    }

    public Locacao agora(){
        return this.locacao;
    }
}
