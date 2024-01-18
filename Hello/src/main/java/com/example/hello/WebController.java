package com.example.hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class WebController {
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/form")
	public String form() {
		return "form";
	}
	
	@PostMapping("/processForm")
	public String processForm(
			@RequestParam("id") int id,
			@RequestParam("name")String name,
			Model model
			){
		model.addAttribute("id",id);
		model.addAttribute("name",name);
		return "result";
	}
	
//	@GetMapping("/hello")
//	@ResponseBody
//	public String hello(@RequestParam(name="name") String name) {
//		return "Hello!"+ name ;
//	}
	
	
	
}
