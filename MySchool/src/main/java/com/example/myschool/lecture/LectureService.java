package com.example.myschool.lecture;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.myschool.lecture.domain.Lecture;
import com.example.myschool.lecture.repository.LectureRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LectureService {
	private final LectureRepository lectureRepository;

	public List<LectureDto> getLectures(String title) {
		ArrayList<LectureDto> lectures = new ArrayList<LectureDto>();

		if (title == null) {
			lectureRepository.findAll().forEach(t -> {
				LectureDto dto = LectureDto.builder()
				.lectureId(t.getLectureId())
				.teacherId(t.getTeacher().getTeacherId())
				.title(t.getTitle())
				.description(t.getDescription())
				.build();
				lectures.add(dto);
			});
		} else {
			lectureRepository.findByTitleContainsIgnoreCase(title).forEach(t -> {
				LectureDto dto = LectureDto.builder()
						.lectureId(t.getLectureId())
						.teacherId(t.getTeacher().getTeacherId())
						.title(t.getTitle())
						.description(t.getDescription())
						.build();
						lectures.add(dto);
			});
		}

		return lectures;
	}
	
	public LectureDto getLecture(int id) {

		Optional<Lecture> result = lectureRepository.findById(id);
		if (result == null) {
			Lecture t =result.get();
				LectureDto dto = LectureDto.builder()
				.lectureId(t.getLectureId())
				.teacherId(t.getTeacher().getTeacherId())
				.title(t.getTitle())
				.description(t.getDescription())
				.build();

				return dto;
		} else {
			return null;
		}

	}
	
	
	
}
