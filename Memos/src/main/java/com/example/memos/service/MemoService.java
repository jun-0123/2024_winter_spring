package com.example.memos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.memos.domain.Memo;
import com.example.memos.domain.User;
import com.example.memos.dto.AddMemoRequest;
import com.example.memos.dto.GetMemoResponse;
import com.example.memos.repositoy.MemoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemoService {
	private final MemoRepository memoRepository;
	private final UserService userService;
	
	private GetMemoResponse entityToResponse(Memo memo) {
		return new GetMemoResponse(
				memo.getId(),
				memo.getBody(),
				memo.getCreatedAt(),
				memo.getUser().getUsername(),
				memo.getFileName()
				);
	}
	
	private Memo requstToEntity(AddMemoRequest dto) {
		User user = userService.findById(dto.getUsername());
		
		Memo memo =  new Memo(
				null,
				dto.getBody(),
				dto.getFileName(),
				null,
				user
				);
		System.out.println(memo.getBody());
		System.out.println(memo.getUser().getUsername());
		return memo;
	}
	
	   public List<GetMemoResponse> getMemosByUser(String username) {
		      return memoRepository.findByUser_usernameOrderByCreatedAtDesc(username).stream()
		            .map(memo -> entityToResponse(memo))
		            .collect(Collectors.toList());
		   }
	
	public Long addMemo(AddMemoRequest dto) {
		Memo result = memoRepository.save(requstToEntity(dto));
		return result.getId();
	}
	
	public void deleteMemo(Long id) {
		memoRepository.deleteById(id);
	}
}
