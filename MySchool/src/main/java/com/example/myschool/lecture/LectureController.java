package com.example.myschool.lecture;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController //json객체를 받을수 있는 어쩌구
@RequestMapping("/lectures")

public class LectureController {
	
	private final LectureService lectureService;
	
	@GetMapping("")
	public List<LectureDto> getLectures(

	@RequestParam(name="title", required=false)String title){

	return lectureService.getLectures(title);
	}

	@GetMapping("/{id}")
	public LectureDto getlecture(

	@PathVariable("id")int id) {

	return lectureService.getLecture(id);
	}

}