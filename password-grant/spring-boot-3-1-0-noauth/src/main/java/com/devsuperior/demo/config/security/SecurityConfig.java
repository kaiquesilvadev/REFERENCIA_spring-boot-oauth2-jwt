package com.devsuperior.demo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
		return http.build();
	}

}