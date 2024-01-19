package com.example.memos.service;

import java.time.Duration;

import org.hibernate.engine.jdbc.dialect.spi.DatabaseMetaDataDialectResolutionInfoAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.memos.config.JwtProperties;
import com.example.memos.domain.RefreshToken;
import com.example.memos.domain.User;
import com.example.memos.dto.CreateAccessTokenRequest;
import com.example.memos.dto.CreateAccessTokenResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenService {
	private final JwtTokenProvider tokenProvider;
	private final RefreshTokenService refreshTokenService;
	private final UserService userService;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;
	private final JwtProperties jwtProperties;
	
	public CreateAccessTokenResponse createAccessToken(CreateAccessTokenRequest request) throws IllegalAccessException {
		if(request.getUsername() !=null && request.getPassword() != null) {
			
			User user = userService.findById(request.getUsername());
			if(bcryptPasswordEncoder.matches(request.getPassword(),user.getPassword())) {
				return new CreateAccessTokenResponse(
						tokenProvider.createToken(user, Duration.ofMinutes(jwtProperties.getDuration())),
						createRefreshToken(user));
			}
		}
		else if(request.getRefreshToken()!=null) {
			if(tokenProvider.isValidToken(request.getRefreshToken())) {
				String username = refreshTokenService.findByRefreshToken(request.getRefreshToken()).getUsername();
				User user = userService.findById(username);
				return new CreateAccessTokenResponse(
							tokenProvider.createToken(user, Duration.ofMinutes(jwtProperties.getDuration())),
								null);	
			}
		}
		throw new IllegalArgumentException("Invaild password");
	}
	
	public String createRefreshToken(User user) throws IllegalAccessException{
		String token = tokenProvider.createToken(user, Duration.ofHours(jwtProperties.getRefreshDuration()));
		RefreshToken refreshToken = refreshTokenService.findByUsername(user.getUsername());
		refreshToken.setRefreshToken(token);
		refreshTokenService.save(refreshToken);
		return token;
	}
}
