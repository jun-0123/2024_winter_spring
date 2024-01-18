package com.example.cafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cafe.domain.Member;

public interface MemberRepository  extends JpaRepository<Member, String>{

}
