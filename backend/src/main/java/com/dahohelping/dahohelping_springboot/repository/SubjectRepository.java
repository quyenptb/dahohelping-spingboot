package com.dahohelping.dahohelping_springboot.repository;

import com.dahohelping.dahohelping_springboot.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    @Query("SELECT s FROM Subject s WHERE s.name = ?1")
    Optional<Subject> findByName(String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM Subject s WHERE s.name = ?1")
    void deleteByName(String name);

    @Query("SELECT s FROM Subject s WHERE s.name LIKE %?1%")
    List<Subject> findByNameContaining(String keyword);

    @Query("SELECT s FROM Subject s WHERE s.major.id = ?1")
    List<Subject> findByMajorId(Integer majorId);

    @Query("SELECT s FROM Subject s JOIN s.major m JOIN m.faculty f WHERE f.id = ?1")
    List<Subject> findByFacultyId(Integer facultyId);

    @Query("SELECT s FROM Subject s JOIN s.major m JOIN m.faculty f JOIN f.university u WHERE u.id = ?1")
    List<Subject> findByUniversityId(Integer universityId);
}
