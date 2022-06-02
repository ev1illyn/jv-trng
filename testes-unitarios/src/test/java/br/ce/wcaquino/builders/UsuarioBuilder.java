package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Usuario;

//padrões de projeto change method e builder
public class UsuarioBuilder {

	private Usuario usuario;
	
	private UsuarioBuilder() {}
	
	//método estático pode ser chamado sem criar instância dessa classe
	public static UsuarioBuilder umUsuario(){
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Evillyn");
		return builder;
	}
	
	public Usuario agora() {
		return usuario;
	}
}
