package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Usuario;

//padr�es de projeto change method e builder
public class UsuarioBuilder {

	private Usuario usuario;
	
	private UsuarioBuilder() {}
	
	//m�todo est�tico pode ser chamado sem criar inst�ncia dessa classe
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
