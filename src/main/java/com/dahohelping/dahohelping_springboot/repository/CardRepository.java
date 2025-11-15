package com.dahohelping.dahohelping_springboot.repository;

import com.dahohelping.dahohelping_springboot.entity.Card;
import com.dahohelping.dahohelping_springboot.service.dto.request.CardFilterDto;
import com.dahohelping.dahohelping_springboot.service.dto.response.CardResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    String CARD_DETAIL_SELECT = "SELECT new com.dahohelping.dahohelping_springboot.service.dto.response.CardResponse("
            + "c.id, c.title, c.award, c.text, c.createdDate, c.images, c.isReported, c.isAnswered, "
            + "u.id, u.username, u.avatar, "
            + "d.name, "
            + "uni.name, uni.code, "
            + "f.name, "
            + "m.name, "
            + "s.name) ";

    String CARD_DETAIL_JOINS = "FROM Card c "
            + "LEFT JOIN c.user u "
            + "LEFT JOIN c.dahohelping d "
            + "LEFT JOIN c.subject s "
            + "LEFT JOIN c.major m "
            + "LEFT JOIN c.faculty f "
            + "LEFT JOIN c.university uni ";

    @Query(CARD_DETAIL_SELECT + CARD_DETAIL_JOINS + "ORDER BY c.createdDate DESC")
    List<CardResponse> findAllCardDetails();

    @Query(CARD_DETAIL_SELECT + CARD_DETAIL_JOINS + "WHERE c.id = :cardId")
    Optional<CardResponse> findCardDetailById(@Param("cardId") Integer cardId);

    @Query(CARD_DETAIL_SELECT + CARD_DETAIL_JOINS + "WHERE u.username = :username ORDER BY c.createdDate DESC")
    List<CardResponse> findCardDetailsByUsername(@Param("username") String username);

    @Query(CARD_DETAIL_SELECT + CARD_DETAIL_JOINS + "WHERE c.title = :title")
    Optional<CardResponse> findCardDetailByTitle(@Param("title") String title);

    @Query(CARD_DETAIL_SELECT + CARD_DETAIL_JOINS
            + "WHERE (:#{#filter.dahoId} IS NULL OR d.id = :#{#filter.dahoId}) "
            + "AND (:#{#filter.uniId} IS NULL OR uni.id = :#{#filter.uniId}) "
            + "AND (:#{#filter.falId} IS NULL OR f.id = :#{#filter.falId}) "
            + "AND (:#{#filter.majId} IS NULL OR m.id = :#{#filter.majId}) "
            + "AND (:#{#filter.subId} IS NULL OR s.id = :#{#filter.subId}) "
            + "ORDER BY c.createdDate DESC")
    List<CardResponse> findFilteredCardDetails(@Param("filter") CardFilterDto filter);

    @Query(CARD_DETAIL_SELECT + CARD_DETAIL_JOINS
            + "WHERE c.title LIKE %:keyword% "
            + "ORDER BY c.createdDate DESC")
    List<CardResponse> findCardDetailsByTitleContaining(@Param("keyword") String keyword);

    @Query("SELECT c FROM Card c WHERE c.title = ?1")
    Card findByTitle(String title);

    @Modifying
    @Transactional
    @Query("DELETE FROM Card c WHERE c.title = ?1")
    void deleteByTitle(String title);
}