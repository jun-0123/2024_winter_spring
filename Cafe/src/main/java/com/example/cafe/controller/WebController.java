package com.example.cafe.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.cafe.domain.Admin;
import com.example.cafe.domain.Member;
import com.example.cafe.repository.MemberRepository;
import com.example.cafe.service.AdminService;
import com.example.cafe.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class WebController {
	private final AdminService adminService;
	private final MemberService memberService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/signin")
	public String signIn(@RequestParam(name = "message", required = false) String message, Model model) {
		model.addAttribute("message", message);
		return "signin";
	}

//	회원가입 
	@GetMapping("/join")
	public String join() {
		return "join";
	}

	@PostMapping("/join")
	public String postJoin(@RequestParam("username") String username, @RequestParam("password") String password,
			RedirectAttributes ra) {
		Admin result = adminService.addAdmin(new Admin(username, password));
		ra.addAttribute("message", "User Added. Please Sign in");
		return "redirect:/signin";
	}
//	멤버 작성
	  @GetMapping("/members") 
	  public String members(Model model, Principal user) {
	  model.addAttribute("members",memberService.getMembers()); 
	  return "members"; }
	  
	  
	  @PostMapping("/members")
		public String members(
				@RequestParam("body") String body,
				Principal user) {
			memberService.addMember(new Member(body,0));
			return "redirect:/members";
		}
	  
//	  포인트 증감
	@PostMapping("/updatePoint/{pno}")
	  public String updatePoint(
			  @PathVariable("pno") String pno,
			  @RequestParam("point") int point, Principal user) {
		  Member member = memberService.findbyMember(pno).orElse(null);
		  if (member != null) {
			  member.setPno(pno);
			  member.setPoint(member.getPoint()+point);
		  }
	  			memberService.addMember(member);
	  	
	  	return "redirect:/members";
	  }
	  
	  




}
