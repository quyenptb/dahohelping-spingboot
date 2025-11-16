package com.dahohelping.dahohelping_springboot.repository;

import com.dahohelping.dahohelping_springboot.entity.Notification;
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
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("SELECT n FROM Notification n WHERE n.title = ?1")
    Optional<Notification> findByTitle(String title);

    @Modifying
    @Transactional
    @Query("DELETE FROM Notification n WHERE n.title = ?1")
    void deleteByTitle(String title);

    @Query("SELECT n FROM Notification n WHERE n.title LIKE %?1%")
    List<Notification> findByTitleContaining(String keyword);

    @Query("SELECT n FROM Notification n WHERE n.owner.id = ?1")
    List<Notification> findByOwnerId(Integer ownerId);

    @Query("SELECT n FROM Notification n WHERE n.owner.username = ?1")
    List<Notification> findByOwnerUsername(String ownerName);

    @Query("SELECT n FROM Notification n WHERE n.receiver.id = ?1")
    List<Notification> findByReceiverId(Integer receiverId);

    @Query("SELECT n FROM Notification n WHERE n.receiver.username = ?1")
    List<Notification> findByReceiverUsername(String receiverName);

    @Query("SELECT n FROM Notification n WHERE n.createdDate BETWEEN ?1 AND ?2")
    List<Notification> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
