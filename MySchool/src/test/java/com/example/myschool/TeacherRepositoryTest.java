package com.example.myschool;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.myschool.teacher.domain.Teacher;
import com.example.myschool.teacher.repository.TeacherRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeacherRepositoryTest {

@Autowired
TeacherRepository repository;

@Test
void findAll() {
List<Teacher> list = repository.findAll();
Assertions.assertEquals(list.size(), 2);
}
}