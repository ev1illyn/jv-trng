package br.com.alura.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class AgendamentoEmail {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	@NotBlank(message="{agendamentoEmail.assunto.vazio}")
	@Email(message="{agendamentoEmail.email.vazio}")
	private String email;
	
	@Column
	private Boolean enviado;
	
	@Column
	@NotBlank(message="{agendamentoEmail.email.invalido}")
	private String assunto;
	
	@Column
	@NotBlank(message="{agendamentoEmail.mensagem.vazio}")
	private String mensagem;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getEnviado() {
		return enviado;
	}
	public void setEnviado(Boolean enviado) {
		this.enviado = enviado;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
