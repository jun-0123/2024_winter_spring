package com.example.memos.service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.memos.config.JwtProperties;
import com.example.memos.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtTokenProvider {
	private final JwtProperties jwtProperties;
	private SecretKey key;
	private JwtParser parser;
	
	public String createToken(User user, Duration expriedAt) {
		Date now =new Date();
		Date exp = new Date(now.getTime()+expriedAt.toMillis());
		
		return Jwts.builder()
				.header().add(getHeader()).and()
				.claims()
					.issuedAt(now)
					.issuer(jwtProperties.getIssuer())
					.subject(user.getUsername())
					.expiration(exp)
					.add("role",user.getRole()).and()
				.signWith(getKey(), Jwts.SIG.HS256)
				.compact();
	}
	
	public boolean isValidToken(String token) {
		if(parser == null) {
			parser = Jwts.parser().verifyWith(getKey()).build();
		}try {
			parser.parseSignedClaims(token);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public Claims getClaims(String token) {
		if(parser == null) {
			parser = Jwts.parser().verifyWith(getKey()).build();
		}
		try {
			Jws<Claims> jws= parser.parseSignedClaims(token);
			return jws.getPayload();
		}catch(Exception e) {
			return null;
		}
	}
	
	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token);
		String role = claims.get("role",String.class);
		Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role));
		
		UserDetails userDetails = org.springframework.security.core.userdetails.User
				.withUsername(claims.getSubject())
				.password(claims.getSubject())
				.roles(role)
				.build();
		
		return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
	}
	
	private SecretKey getKey() {
		if(key == null) {
			key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtProperties.getSecretKey()));
		}
		return key;
		
	}
	
	private Map<String, Object> getHeader(){
		Map<String, Object> header = new HashMap<>();
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		return header;
	}
	
}
