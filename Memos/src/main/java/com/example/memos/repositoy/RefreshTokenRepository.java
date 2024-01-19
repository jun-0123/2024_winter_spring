package com.example.memos.repositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.memos.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
	public Optional<RefreshToken> findByUsername(String username);
	public Optional<RefreshToken> findByRefreshToken(String refreshToken);

}
