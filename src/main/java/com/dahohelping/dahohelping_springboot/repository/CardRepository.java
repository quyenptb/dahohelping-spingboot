package com.dahohelping.dahohelping_springboot.repository;

import com.dahohelping.dahohelping_springboot.entity.Card;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    @Query("SELECT c FROM Card c WHERE c.title = ?1")
    Card findByTitle(String title);

    @Modifying
    @Transactional
    @Query("DELETE FROM Card c WHERE c.title = ?1")
    void deleteByTitle(String title);

    // Spring JPA already has a deleteById(id), so I don't need to override it
/*
    @Modifying
    @Transactional
    @NotNull
    @Query("DELETE FROM Card c WHERE c.cardId = ?1")
    void deleteById(Integer cardId); */

    @Query("SELECT c FROM Card c WHERE c.title LIKE %?1%")
    List<Card> findByTitleContaining(String keyword);

    @Query("SELECT c FROM Card c WHERE c.dahohelping.id = ?1")
    List<Card> findByDahohelpingId(Integer dahoId);

    @Query("SELECT c FROM Card c WHERE c.dahohelping.name = ?1")
    List<Card> findByDahohelpingName(String dahoName);

    @Query("SELECT c FROM Card c WHERE c.university.id = ?1")
    List<Card> findByUniversityId(Integer universityId);

    @Query("SELECT c FROM Card c WHERE c.university.name = ?1")
    List<Card> findByUniversityName(String universityName);

    @Query("SELECT c FROM Card c WHERE c.university.code = ?1")
    List<Card> findByUniversityCode(String universityCode);

    @Query("SELECT c FROM Card c WHERE c.user.id = ?1")
    List<Card> findByUserId(Integer userId);

    @Query("SELECT c FROM Card c WHERE c.user.username = ?1")
    List<Card> findByUsername(String username);

    @Query("SELECT c FROM Card c WHERE c.subject.id = ?1")
    List<Card> findBySubjectId(Integer subjectId);

    @Query("SELECT c FROM Card c WHERE c.subject.name = ?1")
    List<Card> findBySubjectName(String subjectName);

    @Query("SELECT c FROM Card c WHERE c.createdDate = ?1")
    List<Card> findByDate(LocalDateTime date);

    @Query("SELECT c FROM Card c WHERE c.createdDate BETWEEN ?1 AND ?2")
    List<Card> findByCreatedDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("SELECT c FROM Card c WHERE c.isReported = true")
    List<Card> findReportedCard();

    @Query("SELECT c FROM Card c WHERE c.isAnswered = false")
    List<Card> findIsNotCommentedCard();
}
