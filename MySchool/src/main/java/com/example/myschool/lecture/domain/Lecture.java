package com.example.myschool.lecture.domain;

import com.example.myschool.teacher.domain.Teacher;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Lecture {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer lectureId;

	@Column(length = 50)
	private String title;

	@Column(length = 200)
	private String description;

	@ManyToOne
	@JoinColumn(name="teacher_id")
	private Teacher teacher;
	
	
	
}
