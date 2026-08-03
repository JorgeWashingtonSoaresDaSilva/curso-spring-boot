package com.jwss.studio.springboot.curso.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class PessoaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull(message = "Nome não pode ser nulo")
	@NotEmpty(message = "Nome não pode ser vazio")
	private String nome;
	@NotNull(message = "Sobreome não pode ser nulo")
	@NotEmpty(message = "Sobreome não pode ser vazio")
	private String sobrenome;
	@Min(value = 18,message = "Idade invávilida")
	private int idade;
	@DateTimeFormat(pattern = "yyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	private String sexo;
	@Enumerated(EnumType.STRING)
	private Cargo cargo;
	@ManyToOne
	private ProfissaoEntity profissao;
	@Lob
	private byte[] curriculo;

	private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String ibge;

	@OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<TelefoneEntity> telefones;


	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getIbge() {
		return ibge;
	}
	public void setIbge(String ibge) {
		this.ibge = ibge;
	}
	public List<TelefoneEntity> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<TelefoneEntity> telefones) {
		this.telefones = telefones;
	}
	public String getNumero() {return numero;}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public ProfissaoEntity getProfissao() {
		return profissao;
	}
	public void setProfissao(ProfissaoEntity profissao) {
		this.profissao = profissao;
	}
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Date getDataNascimento() {return dataNascimento;}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public byte[] getCurriculo() {return curriculo;}

	public void setCurriculo(byte[] curriculo) {this.curriculo = curriculo;}
}
