package com.example.myschool.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.myschool.lecture.LectureService;
import com.example.myschool.teacher.domain.Teacher;
import com.example.myschool.teacher.service.TeacherService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller //페이지 리턴?
public class WebController {
	private final TeacherService teacherService;
	private final LectureService lectureService;
	
	@GetMapping("/")
	public String index(Model model) {
		
		Teacher teacher = Teacher.builder()
				.teacherId(1)
				.name("JavaKim")
				.description("No.1 Java Teacher")
				.build();
		
		model.addAttribute("username", "hello");
		model.addAttribute("teacher", teacher);
		return "index";
	
				
	}
	@GetMapping("/view-teachers")
	public String theachers(Model model) {
		model.addAttribute("teachers",teacherService.getTeachers(null));
		return "teachers";
	}
	@GetMapping("/view-teachers/{id}")
	public String theachers(@PathVariable("id") int id, Model model) {
		model.addAttribute("teacher",teacherService.getTeacher(id));
		return "teacher_info";
	}
	@GetMapping("/view-lectures")
	public String lechuers(Model model) {
		model.addAttribute("lectures",lectureService.getLectures(null));
		return "lectures";
	}
	

}
