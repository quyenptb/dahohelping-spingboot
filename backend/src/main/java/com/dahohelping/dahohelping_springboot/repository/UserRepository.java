package com.dahohelping.dahohelping_springboot.repository;

import com.dahohelping.dahohelping_springboot.entity.Card;
import com.dahohelping.dahohelping_springboot.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Boolean existsByUsername(String username);
    @Query("SELECT u FROM User u ORDER BY u.score DESC")
    List<User> getRanking(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    User _findById(Integer userId);

    @Query("DELETE FROM User u WHERE u.username = ?1")
    void deleteByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.googleId = ?1")
    User findByGoogleId(String googleId);

    @Query("SELECT u FROM User u WHERE u.username LIKE %?1%")
    List<User> findByUsernameContaining(String keyword);

}

