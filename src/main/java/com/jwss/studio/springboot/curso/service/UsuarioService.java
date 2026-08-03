package com.jwss.studio.springboot.curso.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	// Repositório injetado para realizar as operações de banco de dados da entidade Usuario
	private final UsuarioRepository usuarioRepository;

	// Algoritmo Bcrypt utilizado para criptografar as senhas em formato de Hash seguro
	private final BCryptPasswordEncoder passwordEncoder;

	/**
	 * Construtor da classe utilizado pelo Spring para gerenciar a injeção de dependências.
	 * Instancia o PasswordEncoder internamente para evitar problemas de dependência circular na configuração.
	 */
	UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	/**
	 * Método responsável por cadastrar um novo usuário no sistema de forma segura.
	 *
	 * @param usuario Objeto contendo os dados do usuário com a senha ainda em texto limpo.
	 * @return O objeto UsuarioEntity salvo no banco de dados com a senha já criptografada.
	 */
	public UsuarioEntity salvaNovoUsuario(UsuarioEntity usuario) {
		// 1. Captura a senha em texto plano (ex: "123"), gera o hash BCrypt seguro e atualiza no objeto
		String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);

		// 2. Persiste o usuário com a senha protegida na tabela do banco de dados
		return usuarioRepository.save(usuario);
	}

	/**
	 * Método do Spring Security acionado automaticamente quando o usuário clica em "Entrar" na tela de login.
	 *
	 * @param username O texto digitado pelo usuário no campo de login do formulário.
	 * @return O objeto da entidade que implementa UserDetails caso as credenciais sejam válidas.
	 * @throws UsernameNotFoundException Disparada se o login fornecido não constar no banco de dados.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 1. Executa a consulta customizada no repositório buscando pelo campo de login
		UsuarioEntity usuario = usuarioRepository.findUsuarioByLogin(username);

		// 2. Caso a consulta não retorne nenhum registro, interrompe o fluxo e nega o acesso
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuário não foi encontrado");
		}

		// 3. Retorna o usuário encontrado (o Spring Security validará a senha hash por baixo dos panos)
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(),
				true, true, true, usuario.getAuthorities());
	}
}
