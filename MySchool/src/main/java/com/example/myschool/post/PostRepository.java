package com.example.myschool.post;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Integer>{
	List<Post> findAllByOrderByPostIdDesc();
}
