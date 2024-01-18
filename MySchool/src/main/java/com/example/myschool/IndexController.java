package com.example.myschool;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myschool.comment.Comment;
import com.example.myschool.comment.CommentRepository;
import com.example.myschool.comment.CommentService;
import com.example.myschool.post.Post;
import com.example.myschool.post.PostRepository;
import com.example.myschool.post.PostService;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Controller

public class IndexController {
	private final PostService postService;
	private final CommentService commentService;
	@Autowired
	private PostRepository postRepository;
	
	private final CommentRepository commentRepository;
//	게시글 전체 조회
	@GetMapping("/posts")
	public String index(Model model) {
		model.addAttribute("posts",postService.getPosts());
		return "posts";
	}
//	게시글 한 건 조회
	@GetMapping("/posts/{id}")
	public String getPost( Model model,@PathVariable("id") int id) {
		model.addAttribute("post",postService.getPost(id));
		model.addAttribute("comments",commentService.getComments(id));
		return "post";
	}
//	게시글 작성
	@GetMapping("/addpost")
	public String savePost() {
		return "addpost";
	}
	
	@PostMapping("/newpost")
	public String newpost(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("password") Integer password) {
		
		Post post = Post.builder()
				.title(title)
				.content(content)
				.password(password)
				.date(new Date())
				.build();
		postRepository.save(post);
		
		return "redirect:/posts";
	}
	// 게시글 수정
	@PostMapping("/posts/update/{id}")
	public String updatepost(
			@PathVariable("id") int id, 
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("password") Integer password) {
		
		Post post = postRepository.findById(id).orElse(null);
		if (post != null) {
	        post = Post.builder()
	                .postId(post.getPostId())
	                .title(title)
	                .content(content)
	                .password(password)
	                .date(new Date())
	                .build();
	        
		postRepository.save(post);
		}
		return "redirect:/posts";
	}
	
	@GetMapping("/posts/update/{id}")
	public String updatePost( Model model,@PathVariable("id") int id) {
		model.addAttribute("post",postService.getPost(id));
		return "postupdate";
	}
	
//	게시글 삭제
	@GetMapping("/posts/delete/{id}")
	public String deletePost( Model model,@PathVariable("id") int id) {
		model.addAttribute("post",postService.getPost(id));
		return "deletepost";
	}
	@PostMapping("/posts/delete/{id}")
	public String deletePost(
			@PathVariable("id") int id) {
		postService.deletePost(id);
		return "redirect:/posts";
	}
	
	// 댓글 작성
	@PostMapping("/newcomment/{id}")
	public String newcomment(
			@PathVariable("id") int id,
			@RequestParam("content") String content,
			@RequestParam("password") Integer password) {
		Post post = postService.getPost(id);
		Comment comment = Comment.builder()
				.content(content)
				.password(password)
				.date(new Date())
				.post(post)
				.build();
		commentRepository.save(comment);
		
		return "redirect:/posts/{id}";
	}
	
//	댓글 수정
	@PostMapping("/comment/update/{id}")
	public String updatecomment(
			@PathVariable("id") int id, 
			@RequestParam("content") String content,
			@RequestParam("password") Integer password) {
		Comment comment = commentRepository.findById(id).orElse(null);
		Integer postId = comment.getPost().getPostId();
		Post post = postService.getPost(postId);
		if (comment != null) {
	        comment = Comment.builder()
	        		.commentId(id)
	                .content(content)
	                .password(password)
	                .date(new Date())
	                .post(post)
	                .build();
	        
		commentRepository.save(comment);
		
		return "redirect:/posts/"+postId ;
		}
		return "redirect:/posts/";
	}
	
	@GetMapping("/comment/update/{id}")
	public String updateComment( Model model,@PathVariable("id") int id) {
		model.addAttribute("comment",commentService.getcomment(id));
		return "commentupdate";
	}

//	댓글 삭제
	@GetMapping("/comment/delete/{id}")
	public String deleteComment( Model model,@PathVariable("id") int id) {
		model.addAttribute("comment",commentService.getcomment(id));
		return "commentdelete";
	}
	@PostMapping("/comment/delete/{id}")
	public String deleteComment(
			@PathVariable("id") int id) {
		Comment comment = commentRepository.findById(id).orElse(null);
		Integer postId = comment.getPost().getPostId();

		commentService.deleteComment(id);
		return "redirect:/posts/"+postId;
	}
	
	

}
