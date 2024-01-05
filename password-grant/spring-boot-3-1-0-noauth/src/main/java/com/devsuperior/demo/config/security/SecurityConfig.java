package com.devsuperior.demo.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	/**
	 * Configuração do Bean para fornecer um objeto BCryptPasswordEncoder.
	 * O BCryptPasswordEncoder é um algoritmo de hash de senhas usado para criptografar e verificar senhas.
	 * Ele aplica automaticamente o "salting" e é recomendado para segurança de senhas em aplicações.
	 * Este método retorna uma instância de BCryptPasswordEncoder, que pode ser injetada em outros componentes
	 * para realizar operações seguras de codificação e verificação de senhas.
	 */
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	@Bean
	@Profile("test")
	@Order(1)
	public SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {

		http.securityMatcher(PathRequest.toH2Console()).csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
		return http.build();
	}	
	
	@Bean
	@Order(2)
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
		return http.build();
	}
	


}