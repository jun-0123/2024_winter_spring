package com.example.memos.controller;


import java.security.Principal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.memos.dto.AddMemoRequest;
import com.example.memos.dto.AddUserRequest;
import com.example.memos.dto.AddUserResponse;
import com.example.memos.dto.FileResponse;
import com.example.memos.service.FileService;
import com.example.memos.service.MemoService;
import com.example.memos.service.UserService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class WebController {
	private final UserService userService;
	private final MemoService memoService;
	private final FileService fileService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/signin")
	public String signIn(
			@RequestParam(name="message", required=false)String message,
			Model model) {
		model.addAttribute("message",message);
		return "signin";
	}
	
//	회원가입 
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	@PostMapping("/join")
	public String postJoin(
			@RequestParam ("username") String username,
			@RequestParam ("password") String password,
			RedirectAttributes ra) {
		AddUserResponse result = userService.addUser(new AddUserRequest(username,password));
		ra.addAttribute("message", "User Added. Please Sign in");
		return "redirect:/signin";
	}
//	메모 작성
	@GetMapping("/memos")
	public String memos(Model model, Principal user) {
		model.addAttribute("memos",memoService.getMemosByUser(user.getName()));
		return "memos";
	}
	
	@PostMapping("/memos")
	public String memos(
			@RequestParam("body") String body,
			@RequestParam("file") MultipartFile file,
			Principal user) {
		
		String savedName = fileService.uploadFile(file);
		
		memoService.addMemo(new AddMemoRequest(user.getName(), body, savedName));
		return "redirect:/memos";
	}
	
//	메모삭제
	@PostMapping("/memos/delete/{id}")
	public String deleteMemo(
			@PathVariable("id") Long id) {
		memoService.deleteMemo(id);
		return "redirect:/memos";
	}
	
//	파일 조회 경로 추가
	@GetMapping("/uploads/{name}")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(@PathVariable("name") String fileName) {
		FileResponse res = fileService.getFile(fileName);
		
		if(res.getContentType() != null) {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type",res.getContentType());
			return new ResponseEntity<>(res.getBytes(), header, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	

}
