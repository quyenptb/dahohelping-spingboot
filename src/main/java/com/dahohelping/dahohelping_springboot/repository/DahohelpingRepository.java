package com.dahohelping.dahohelping_springboot.repository;

import com.dahohelping.dahohelping_springboot.entity.Dahohelping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DahohelpingRepository extends JpaRepository<Dahohelping, Integer> {

    @Query("SELECT d FROM Dahohelping d WHERE d.name = ?1")
    Optional<Dahohelping> findByName(String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dahohelping d WHERE d.name = ?1")
    void deleteByName(String name);

    @Query("SELECT d FROM Dahohelping d WHERE d.name LIKE %?1%")
    List<Dahohelping> findByNameContaining(String keyword);
}
