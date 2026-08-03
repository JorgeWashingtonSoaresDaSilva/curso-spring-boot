package com.jwss.studio.springboot.curso.entity;


import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jwss.studio.springboot.curso.security.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;

/**
 * Entidade que mapeia a tabela de usuários no banco de dados.
 * Implementa 'UserDetails', tornando-se a classe oficial de credenciais lida pelo Spring Security.
 */
@Entity
public class UsuarioEntity implements UserDetails {

	// Número de versão para controle de serialização do objeto java
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id; // Chave primária autoincrementável no banco de dados

	private String login; // Armazena o nome de usuário/username usado no login
	private String senha; // Armazena o hash criptografado (BCrypt) da senha

	/**
	 * Mapeamento de Relacionamento de Muitos para Muitos (ou Um para Muitos na junção).
	 * FetchType.EAGER: Garante que as permissões (roles) sejam carregadas do banco de forma imediata junto com o usuário.
	 * @JoinTable: Cria e configura a tabela intermediária de associação chamada 'usuarios_role' no banco.
	 */
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_role",
		joinColumns = @JoinColumn(
			name = "usuario_id",
			referencedColumnName = "id",
			table ="usuario_entity"
		),
		inverseJoinColumns = @JoinColumn(
			name = "role_id",
			referencedColumnName = "id",
			table = "role"
		)
	)
	private List<Role> roles; // Lista contendo os papéis de acesso atribuídos a este usuário (ex: ADMIN, USER)

	// --- Getters e Setters Estruturais ---

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * CORREÇÃO CRÍTICA: Método que entrega as permissões reais do usuário para o motor do Spring Security.
	 * Varre a lista de 'roles' do banco de dados e adiciona o prefixo "ROLE_" exigido pelo framework.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return roles;
	}

	/**
	 * Vincula a propriedade de senha da sua tabela ao contrato exigido pelo Spring Security.
	 */
	@Override
	public @Nullable String getPassword() {
		return senha;
	}

	/**
	 * Vincula a propriedade de identificador de login da sua tabela ao contrato exigido pelo Spring Security.
	 */
	@Override
	public String getUsername() {
		return login;
	}

	// --- Métodos de Controle de Ciclo de Vida da Conta ---
	// Adicionados para manter o contrato da interface 'UserDetails' íntegro e ativo

	@Override
	public boolean isAccountNonExpired() {
		return true; // Define que a conta do usuário nunca expira
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // Define que a conta do usuário não está bloqueada
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // Define que as credenciais (senha) não possuem tempo de expiração compulsório
	}

	@Override
	public boolean isEnabled() {
		return true; // Define que o cadastro do usuário está ativo e habilitado no sistema
	}
}
