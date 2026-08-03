package com.jwss.studio.springboot.curso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = {"com.jwss.studio.springboot.curso.entity",
		"com.jwss.studio.springboot.curso.security"})
@ComponentScan(basePackages = {"com.*"})
@EnableJpaRepositories(basePackages = {"com.jwss.studio.springboot.curso.repository"} )
@EnableTransactionManagement

public class CursoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursoApplication.class, args);

		/*BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = encoder.encode("admin");
		System.out.println("admin criptografado: " +result ); */

	}

}
