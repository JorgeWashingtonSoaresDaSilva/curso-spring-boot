package com.jwss.studio.springboot.curso.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;


import jakarta.persistence.Entity;


@Entity
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;


    @Override
    public @Nullable String getAuthority() {
        return "";
    }
}
