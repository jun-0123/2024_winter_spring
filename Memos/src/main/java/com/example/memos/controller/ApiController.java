package com.example.memos.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.memos.dto.AddMemoRequest;
import com.example.memos.dto.AddUserRequest;
import com.example.memos.dto.AddUserResponse;
import com.example.memos.dto.CreateAccessTokenRequest;
import com.example.memos.dto.CreateAccessTokenResponse;
import com.example.memos.dto.GetMemoResponse;
import com.example.memos.service.FileService;
import com.example.memos.service.MemoService;
import com.example.memos.service.TokenService;
import com.example.memos.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ApiController {
	private final MemoService memoService;
	private final UserService userService;
	private final TokenService tokenService;
	private final FileService fileService;
	
	@PostMapping("/join")
	public AddUserResponse postJoin(@RequestBody AddUserRequest request) {
		AddUserResponse response = userService.addUser(request);
		return response;
	}
	
	  @PostMapping("/signin")
	   public ResponseEntity<CreateAccessTokenResponse> postSignin(@RequestBody CreateAccessTokenRequest request) throws IllegalAccessException{
	      try {
	         CreateAccessTokenResponse response = tokenService.createAccessToken(request);
	         return ResponseEntity.ok(response);
	      }catch(IllegalArgumentException e) {
	         return ResponseEntity.status(403).body(null);
	      }
	   }
	
	@PostMapping("/token")
	public ResponseEntity<CreateAccessTokenResponse> postToken(
			@RequestBody CreateAccessTokenRequest request
			) throws IllegalAccessException {
		try {
			CreateAccessTokenResponse response = tokenService.createAccessToken(request);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(403).body(null);
		}
	}
	
	@GetMapping("/memos")
	public List<GetMemoResponse> getMemos(Principal user) {
		return memoService.getMemosByUser(user.getName());
	}
	
	@PostMapping("/memos")
	public String memos(
			@RequestParam("body") String body,
			@RequestParam("file") MultipartFile file,
			Principal user) {
		
		String savedName = fileService.uploadFile(file);
		
		memoService.addMemo(new AddMemoRequest(user.getName(), body, savedName));
		return "성공";
	}
	
	
}
