package com.jwss.studio.springboot.curso.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jwss.studio.springboot.curso.service.UsuarioService;

/**
 * Classe de configuração estrutural de segurança baseada em Java.
 * Anotada com @Configuration para indicar que define Beans gerenciados pelo Spring.
 * Anotada com @EnableWebSecurity para desativar a segurança padrão e ativar este fluxo customizado.
 */
@Configuration
@EnableWebSecurity
public class WebConfigSecurity {

    // Dependência da classe de serviço responsável pela lógica de busca do usuário no banco de dados
    @SuppressWarnings("unused")
	private final UsuarioService usuarioService;

    /**
     * Construtor da classe utilizado para a injeção de dependência explícita.
     * Injeta a instância de UsuarioService diretamente para acoplamento do provedor de dados.
     */
    public WebConfigSecurity(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Configura a cadeia de filtros de segurança (SecurityFilterChain) do Spring Security.
     * Este Bean define quais rotas são protegidas, como o usuário se autentica e como gerenciar sessões.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Desativa a proteção CSRF (Cross-Site Request Forgery).
            // Comum em ambientes de teste ou APIs para simplificar requisições POST/DELETE sem tokens de sincronia.
            .csrf(AbstractHttpConfigurer::disable)

            // Configuração do formulário de autenticação baseado em telas HTML (Form-based Login)
            // O próprio Spring localiza o UsuarioService injetado no construtor para validar o login
            .formLogin(form -> form
                .loginPage("/login") // Define o endereço da tela de login personalizada criada por você
                .defaultSuccessUrl("/cadastropessoa", true) // Rota destino obrigatória caso o login seja bem-saved
                .permitAll() // Garante que a página de login possa ser aberta por usuários não autenticados
            )

            // Gerenciamento da criação e tempo de vida das sessões HTTP no navegador
            .sessionManagement(session -> session
                // IF_REQUIRED: O Spring Security só criará uma sessão no servidor se o estado da tela exigir
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )

            // Configuração das ações executadas durante o encerramento da sessão (Logout)
            .logout(logout -> logout
                .logoutUrl("/logout") // Endpoint mapeado para receber a ação de deslogar
                .logoutSuccessUrl("/") // Redireciona o usuário para a rota pública raiz após sair
                .invalidateHttpSession(true) // Remove e invalida a sessão ativa do usuário no servidor
                .clearAuthentication(true) // Limpa completamente os dados de autenticação do contexto de segurança
            )

            // Bloco de definição e regras de autorização para cada padrão de URL requisitado
            .authorizeHttpRequests(authorize -> authorize
                // 'requestMatchers().permitAll()' define rotas livres de segurança (públicas)
                .requestMatchers(HttpMethod.GET, "/").permitAll() // Permite apenas requisições de leitura na raiz
                .requestMatchers("/materialize/**").permitAll() // Libera pastas de componentes visuais, CSS e JS
                .requestMatchers("/tela").permitAll() // Libera o acesso à nova rota de tela de modelo criada

                // Regra restrita para Administradores (comentada no arquivo original)
                // Se ativada, exige o papel de autoridade de segurança 'ROLE_ADMIN' vindo da entidade do banco
                //.requestMatchers("/cadastropessoa/**").hasRole("GERENTE")
                //.requestMatchers("/telefones/**").hasRole("ADMIN")

                // Correção: Múltiplos perfis acessando as mesmas URLs
                .requestMatchers("/cadastropessoa/**").hasAnyRole("ADMIN","GERENTE")
                .requestMatchers("/telefones/**").hasAnyRole("ADMIN", "USER")

                // Cláusula de fechamento obrigatória: Qualquer endereço não listado acima exigirá login do usuário
                .anyRequest().authenticated()
            );

        // Compila e retorna a cadeia de configurações tratadas
        return http.build();
    }

    /**
     * Declara o componente responsável pela criptografia e correspondência das senhas.
     * Utiliza o algoritmo BCrypt que gera hashes aleatórios e seguros e os valida internamente.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // O método @Bean DaoAuthenticationProvider foi completamente removido daqui
    // para evitar o erro de casting (Casting Exception) interno do Spring.

    /**
     * Expõe o mecanismo centralizador de autenticação (AuthenticationManager) como um Bean estrutural.
     * Ele é o motor interno acionado pelo Spring para disparar as rotas de login e validar permissões.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        // Recupera a instância padrão configurada no ciclo de inicialização do Spring Boot
        return authenticationConfiguration.getAuthenticationManager();
    }
}
