package com.jwss.studio.springboot.curso.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TelefoneEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String numero;
	private String tipo;

	@ManyToOne
	@JoinColumn(
		        name = "pessoa_id",
		        foreignKey = @ForeignKey(name = "fk_pessoa_id")
		    )
	private PessoaEntity pessoa;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public PessoaEntity getPessoa() {
		return pessoa;
	}
	public void setPessoa(PessoaEntity pessoa) {
		this.pessoa = pessoa;
	}




}
