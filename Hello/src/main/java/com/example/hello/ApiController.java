package com.example.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class ApiController {
	
	@GetMapping("")
	public User hello(@RequestParam(name="name", required=false, defaultValue="user") String name) {
		return new User(1, name) ;
	}
	
	@GetMapping("/{id}")
	public User hello(@PathVariable("id") int id) {
		return new User(id,"PathVariable");
	}

}
