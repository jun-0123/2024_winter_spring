package com.example.cafe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cafe.domain.Member;
import com.example.cafe.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private MemberRepository memberRepository;
	
	@Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
	
	public List<Member> getMembers(){
		
		return memberRepository.findAll();
	}

	public Member addMember(Member member) {
		return memberRepository.save(member);
		
	}
	public Optional<Member> findbyMember(String pno) {
		return memberRepository.findById(pno);
	}

	

	
	
	
}
