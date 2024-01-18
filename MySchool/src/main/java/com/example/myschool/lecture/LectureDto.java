package com.example.myschool.lecture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class LectureDto {
	private int lectureId;
	private int teacherId;
	private String title;
	private String description;
}
