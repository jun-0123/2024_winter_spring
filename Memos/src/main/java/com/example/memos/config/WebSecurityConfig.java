package com.example.memos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.memos.dto.TokenExceptionResponse;
import com.example.memos.service.JwtTokenProvider;
import com.example.memos.service.UserDetailsServiceImpl;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
	private final JwtTokenProvider tokenProvider;
	
	@Bean
	@Order(1)
	SecurityFilterChain apiFilterChain(HttpSecurity http)throws Exception{
		http
			.securityMatcher("/api/**")
			.csrf(csrf->csrf.disable())
			.authorizeHttpRequests(authorize ->
					authorize
					.requestMatchers("/api/join","/api/signin","/api/token").permitAll()
					.anyRequest().authenticated()
					)
			.sessionManagement((sessionManagement) ->
					sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling((exceptions) -> 
					exceptions
						.authenticationEntryPoint(jwtException()))
			;
		return http.build();
	}

   @Bean
   SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      return http
            .authorizeHttpRequests(requests ->
	            requests
		            .requestMatchers(HttpMethod.GET, "/css/**", "/js/**", "/images/**","/uploads/**").permitAll() // static files
		            .requestMatchers("/signin", "/signout", "/join", "/").permitAll() // All method
		            .anyRequest().authenticated()) // 나머지 모든 페이지는 로그인 해야 사용 가능
		            .formLogin(login ->
	            login
		            .loginPage("/signin") // default = login
		            .defaultSuccessUrl("/memos")
		            .failureForwardUrl("/"))
		            .logout(logout ->
	            logout
		            .logoutUrl("/signout") // default = logout
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
   
   private AuthenticationEntryPoint jwtException() {
	      AuthenticationEntryPoint ap = (request, response, authException) -> {
	         response.setContentType("application/json");
	         response.setCharacterEncoding("UTF-8");
	         TokenExceptionResponse res = new TokenExceptionResponse();
	         
	         String message = (String) request.getAttribute("TokenException");
	         if(message != null) {
	            response.setStatus(401);
	            res.setResult(message);
	         }else {
	            response.setStatus(403);
	            res.setResult(authException.getMessage());
	         }
	         Gson gson = new Gson();
	         response.getWriter().write(gson.toJson(res));
	      };
	      return ap;
	   }

}