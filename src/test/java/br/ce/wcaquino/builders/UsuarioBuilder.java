package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Usuario;

public class UsuarioBuilder {

    private Usuario usuario;

    private UsuarioBuilder(){

    }

    // Muito parecido com o @Builder do lombok
    public static UsuarioBuilder umUsuario(){
        UsuarioBuilder usuarioBuilder = new UsuarioBuilder();
        usuarioBuilder.usuario = new Usuario();
        usuarioBuilder.usuario.setNome("Test");
        return usuarioBuilder;
    }

    public UsuarioBuilder comNome(String nome){
        usuario.setNome(nome);
        return this;
    }

    public Usuario agora(){
        return this.usuario;
    }
}
