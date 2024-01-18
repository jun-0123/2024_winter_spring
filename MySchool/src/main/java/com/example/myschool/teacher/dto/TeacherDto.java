package com.example.myschool.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class TeacherDto {
	private int teacherId;
	private String name;
	private String description;
}
