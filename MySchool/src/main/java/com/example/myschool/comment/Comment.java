package com.example.myschool.comment;

import java.util.Date;

import com.example.myschool.post.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentId;

	@Column(length=1000)
	private String content;

	@Column
	private Integer password ;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date date ;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="post_id")
	private Post post;
	
	
}
