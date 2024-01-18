package com.example.memos.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="memos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Memo {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   private String body;
   
   private String fileName;
   
   @CreationTimestamp
   private LocalDateTime createdAt;
   
   @ManyToOne(cascade = CascadeType.PERSIST)
   @JoinColumn(name="user_id")
   private User user;
}