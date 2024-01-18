package com.example.memos.repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.memos.domain.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {
   
   /*
    * select *
    * from memos
    * where ?
    * order by created_at desc;
    */
   public List<Memo> findByUser_usernameOrderByCreatedAtDesc(String username);

}