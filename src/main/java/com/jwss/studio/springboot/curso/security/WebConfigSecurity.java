package com.jwss.studio.springboot.curso.security;


import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


/**
 * Classe de configuração estrutural de segurança baseada em Java.
 * Anotada com @Configuration para indicar que define Beans gerenciados pelo Spring.
 * Anotada com @EnableWebSecurity para desativar a segurança padrão e ativar este fluxo customizado.
 */
@Configuration
@EnableWebSecurity
public class WebConfigSecurity {


}
