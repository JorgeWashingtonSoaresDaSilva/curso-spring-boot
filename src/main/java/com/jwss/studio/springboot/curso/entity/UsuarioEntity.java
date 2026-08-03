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


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public @Nullable String getPassword() {
		return "";
	}

	@Override
	public String getUsername() {
		return "";
	}
}
