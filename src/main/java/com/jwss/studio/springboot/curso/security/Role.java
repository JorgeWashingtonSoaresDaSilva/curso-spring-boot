package com.jwss.studio.springboot.curso.security;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nomeRole; // Ex: ROLE_ADMIN, ROLE_GERENTE

    // Construtor padrão obrigatório para o JPA
    public Role() {
    }

    // Construtor utilitário
    public Role(String nomeRole) {
        this.nomeRole = nomeRole;
    }

    @Override
    public String getAuthority() {
        return this.nomeRole;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeRole() {
        return nomeRole;
    }

    public void setNomeRole(String nomeRole) {
        this.nomeRole = nomeRole;
    }
}
