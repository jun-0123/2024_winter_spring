package com.example.cafe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.cafe.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

   @Bean
   SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      return http
            .authorizeHttpRequests(requests ->
            requests
	            .requestMatchers(HttpMethod.GET, "/css/**", "/js/**", "/images/**").permitAll() // static files
	            .requestMatchers("/signin", "/signout", "/join", "/").permitAll() // All method
	            .anyRequest().authenticated()) // 나머지 모든 페이지는 로그인 해야 사용 가능
	        .formLogin(login ->
            login
	            .loginPage("/signin")
	            .defaultSuccessUrl("/members")
	            .failureForwardUrl("/"))
	        .logout(logout ->
            logout
            .logoutUrl("/signout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true))
            .build();
            
   }
   
   @Bean
   AuthenticationManager authenticationManager(
         UserDetailsServiceImpl userDetailsService,
         BCryptPasswordEncoder bCryptPasswordEncoder
         ) {
      DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
      authenticationProvider.setUserDetailsService(userDetailsService);
      authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
      
      return new ProviderManager(authenticationProvider);
   }
   
   @Bean
   BCryptPasswordEncoder bCryptPasswordEncoder() {
      return new BCryptPasswordEncoder();
   }

}
