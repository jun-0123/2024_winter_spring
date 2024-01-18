package com.example.memos.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.memos.domain.User;
import com.example.memos.dto.AddUserRequest;
import com.example.memos.dto.AddUserResponse;
import com.example.memos.repositoy.UserRepository;

import ch.qos.logback.core.pattern.color.BoldCyanCompositeConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private User requestToEntity(AddUserRequest dto) {
//		String password = dto.getPassword(); // 암화화 예정
		String password = bCryptPasswordEncoder.encode(dto.getPassword());
		return new User(dto.getUsername(),password,"user");
	}
	private AddUserResponse entityToResponse(User user) {
		return new AddUserResponse(user.getUsername(),"ok");
	}
	public AddUserResponse addUser(AddUserRequest dto) {
		User result = userRepository.save(requestToEntity(dto));
		return entityToResponse(result);
	}
	
	public User findById(String username) {
		Optional<User> result = userRepository.findById(username);
		if(result.isPresent()) return result.get();
		else return null;
	}
}
