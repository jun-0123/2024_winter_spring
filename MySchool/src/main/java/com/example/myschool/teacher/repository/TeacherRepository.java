package com.example.myschool.teacher.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myschool.teacher.domain.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer>{
	public List<Teacher> findByNameContainsIgnoreCase(String name);
}
