package com.example.cafe.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cafe.domain.Admin;
import com.example.cafe.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {
	private final AdminRepository adminRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Admin requestToEntity(Admin admin) {
		String password = bCryptPasswordEncoder.encode(admin.getPassword());
		return new Admin(admin.getUsername(),password);
	}
	public Admin addAdmin(Admin admin) {
		return adminRepository.save(requestToEntity(admin));
	}
}
