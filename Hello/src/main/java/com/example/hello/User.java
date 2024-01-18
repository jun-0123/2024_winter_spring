package com.example.hello;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor  //생성자 자동화

//@Setter
@Getter
public class User {
	private int id;
	private String name;
}
