package com.jwss.studio.springboot.curso.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwss.studio.springboot.curso.entity.UsuarioEntity;
import com.jwss.studio.springboot.curso.repository.UsuarioRepository;

/**
 * Classe de serviço responsável pelo gerenciamento de usuários e integração
 * com o mecanismo de autenticação nativo do Spring Security.
 *
 * Implementa 'UserDetailsService' para fornecer o método de busca de credenciais durante o login.
 */
@Service
@Transactional
public class UsuarioService implements UserDetailsService {


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}
}
