package com.example.myschool.teacher;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myschool.teacher.dto.TeacherDto;
import com.example.myschool.teacher.service.TeacherService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("/teachers")

public class TeacherController {

	private final TeacherService teacherService;

	@GetMapping("")
	public List<TeacherDto> getTeachers(

	@RequestParam(name="name", required=false)String name){

	return teacherService.getTeachers(name);
	}

	@GetMapping("/{id}")
	public TeacherDto getTeacher(

	@PathVariable("id")int id) {

	return teacherService.getTeacher(id);
	}

}
