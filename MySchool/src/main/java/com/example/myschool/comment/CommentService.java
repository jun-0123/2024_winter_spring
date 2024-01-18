package com.example.myschool.comment;

import java.util.List;

import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
	
	public List<Comment> getComments(int id){
		return commentRepository.findByPost_PostIdOrderByCommentIdDesc(id);
	}
	public Comment getcomment(int id) {
		return commentRepository.findById(id).get();
	}
	
	public void deleteComment(int id) {
		commentRepository.deleteById(id);
	}
}
