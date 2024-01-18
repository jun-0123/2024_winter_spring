package com.example.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cafe.domain.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
	
}
