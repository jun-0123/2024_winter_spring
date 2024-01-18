package com.example.cafe.domain;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name="members")
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	@Id
	private String pno;
	@ColumnDefault("0")
	private Integer point;
}
