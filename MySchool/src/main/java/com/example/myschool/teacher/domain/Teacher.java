package com.example.myschool.teacher.domain;

import java.util.List;

import com.example.myschool.lecture.domain.Lecture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer teacherId;
		
	@Column(length = 50)
	private String name;
	
	@Column(length = 200)
	private String description;
	
	@OneToMany(mappedBy="teacher")
	private List<Lecture> lectures;
	
	
	
	
}
