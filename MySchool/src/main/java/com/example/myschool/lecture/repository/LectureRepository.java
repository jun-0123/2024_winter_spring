package com.example.myschool.lecture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myschool.lecture.domain.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Integer>{
	public List<Lecture> findByTitleContainsIgnoreCase(String title);
}
