package com.example.myschool.teacher.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.myschool.teacher.dto.TeacherDto;
import com.example.myschool.teacher.domain.Teacher;
import com.example.myschool.teacher.repository.TeacherRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TeacherService {

   private final TeacherRepository teacherRepository;

   // 선생님 목록 검색
   public List<TeacherDto> getTeachers(String name) {
      ArrayList<TeacherDto> teachers = new ArrayList<TeacherDto>();
      
      if (name == null) { // 이름이 없으면 전체 검색
         teacherRepository.findAll().forEach(t -> {
            
//            teachers.add(new TeacherDto(t.getTeacherId(), t.getName(), t.getDescription()));
            
            // builder 사용하면 가독성이 좋음
            TeacherDto teacherDto = TeacherDto.builder()
                  .description(t.getDescription())
                  .name(t.getName())
                  .teacherId(t.getTeacherId())
                  .build();
            
            teachers.add(teacherDto);
         });
      } else {
         teacherRepository.findByNameContainsIgnoreCase(name).forEach(t -> {
            teachers.add(new TeacherDto(t.getTeacherId(), t.getName(), t.getDescription()));
         });
      }

      return teachers;
   }

   // 선생님 한 명 검색
   public TeacherDto getTeacher(int id) {
      Optional<Teacher> result = teacherRepository.findById(id); // teacherId가 PK이기 때문에 id로 검색 가능
      
      if (result.isPresent()) { // 있을 때
         Teacher t = result.get();
         return new TeacherDto(t.getTeacherId(), t.getName(), t.getDescription()); // get -> 한 건 조회
      } else { // 없을 때
         return null;
      }
   }
}