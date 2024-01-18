package com.example.memos.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMemoResponse {
	private Long id;
	private String body;
	private LocalDateTime createdAt;
	private String username;
	private String fileName;
}
