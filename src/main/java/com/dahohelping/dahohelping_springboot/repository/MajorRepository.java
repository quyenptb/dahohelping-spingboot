package com.dahohelping.dahohelping_springboot.repository;

import com.dahohelping.dahohelping_springboot.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MajorRepository extends JpaRepository<Major, Integer> {

    @Query("SELECT m FROM Major m WHERE m.name = ?1")
    Optional<Major> findByName(String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM Major m WHERE m.name = ?1")
    void deleteByName(String name);

    @Query("SELECT m FROM Major m WHERE m.name LIKE %?1%")
    List<Major> findByNameContaining(String keyword);

    @Query("SELECT m FROM Major m WHERE m.faculty.id = ?1")
    List<Major> findByFacultyId(Integer id);

    @Query("SELECT m FROM Major m JOIN m.faculty f WHERE f.university.id = ?1")
    List<Major> findByUniversityId(Integer id);

    @Query("SELECT m FROM Major m JOIN m.faculty f WHERE f.university.code = ?1")
    List<Major> findByUniversityCode(String code);

}
