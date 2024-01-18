package com.example.myschool.post;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {
	private final PostService postService;
	
	@GetMapping("")
	public List<Post> getposts() {
		return postService.getPosts();
	}
	
	
}
