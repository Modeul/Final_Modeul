package com.modeul.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.cors()
		.and()
			.csrf().disable()
			.authorizeHttpRequests()
				.requestMatchers("/admin/**").hasAnyRole("2")
				.requestMatchers("/member/**").hasAnyRole("2", "1")
				.anyRequest().permitAll()
		.and()
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/member/stuff/list")
		.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/index");
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();	
	}
	
}
