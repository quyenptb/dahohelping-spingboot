package com.dahohelping.dahohelping_springboot.repository;

import com.dahohelping.dahohelping_springboot.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c WHERE c.text = ?1")
    Optional<Comment> findByText(String text);

    @Override
    Optional<Comment> findById(Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.text = ?1")
    void deleteByText(String text);

    @Query("SELECT c FROM Comment c WHERE c.text LIKE %?1%")
    List<Comment> findByTextContaining(String keyword);

    @Query("SELECT c FROM Comment c WHERE c.cardId = ?1")
    List<Comment> findByCardId(Integer cardId);

    @Query("SELECT c FROM Comment c WHERE c.userId = ?1")
    List<Comment> findByUserId(Integer userId);


    @Query("SELECT c FROM Comment c WHERE c.createdDate BETWEEN ?1 AND ?2")
    List<Comment> findByCreatedDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}