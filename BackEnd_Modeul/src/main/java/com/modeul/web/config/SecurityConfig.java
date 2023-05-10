package com.modeul.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// 사용자의 요청이 들어오면 필터링 해주는 설정을 해주자.
		http
			.cors()
			.and()
				.csrf().disable(); //cross site에서 어떤 요청이 와도 다 받겠다.
			// 	.authorizeHttpRequests() // 권한을 요청하는데 요청들에대한 꼬리를 다는데 요청하는 url에 대한 패턴을 어떠한 유저에게만 허락 할 것인가.
			// 		.requestMatchers("/admin/**").hasAnyRole("2")
			// 		.requestMatchers("/member/**").hasAnyRole("1", "2")
			// 		.anyRequest().permitAll() //나머지 페이지에서는 다른 어떠한 요청에 대해서라도 허락하겠다.
			// .and()
			// 	.formLogin()
			// 		.loginPage("/login") // Get 요청 주소 //시큐리티에서 제공하는 로그인 폼이 아닌 내가 사용할 로그인 폼의 주소를 적어준다.
			// 		.loginProcessingUrl("/login") // POST 요청 주소.(생략하면 Get요청을 했던 주소로 적용된다)
			// 		.defaultSuccessUrl("/member/stuff/list") // 로그인 성공 후 이동 할 url
			// .and()
			// 	.logout()
			// 		.logoutUrl("/member/logout")
			// 		.logoutSuccessUrl("/index");
		
		return http.build();// 설정을 다 했으면 빌드해주자.
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
