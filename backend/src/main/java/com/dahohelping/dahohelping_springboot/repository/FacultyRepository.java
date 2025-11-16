package com.dahohelping.dahohelping_springboot.repository;

import com.dahohelping.dahohelping_springboot.entity.Faculty;
import com.dahohelping.dahohelping_springboot.entity.User;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    @Query("SELECT f FROM Faculty f WHERE f.name = ?1")
    Faculty findByName(String name);

    @Query("SELECT f FROM Faculty f WHERE f.id = ?1")
    Faculty _findById(Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Faculty f WHERE f.name = ?1")
    void deleteByName(String name);

    @Query("SELECT f FROM Faculty f WHERE f.name LIKE %?1%")
    List<Faculty> findByNameContaining(String keyword);

    @Query("SELECT f FROM Faculty f WHERE f.university.id = ?1")
    List<Faculty> findByUniversityId(Integer id);

    @Query("SELECT f FROM Faculty f JOIN f.university u WHERE u.code = ?1")
    List<Faculty> findByUniversityCode(String code);
}
