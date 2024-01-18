package com.example.myschool.post;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostRepository postRepository;
	
	public List<Post> getPosts(){
		return postRepository.findAllByOrderByPostIdDesc();
	}
	
	public Post getPost(int id) {
		return postRepository.findById(id).get();
	}
	
	
	public void deletePost(int id) {
		postRepository.deleteById(id);
	}
	

}
