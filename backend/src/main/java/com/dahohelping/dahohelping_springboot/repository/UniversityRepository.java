package com.dahohelping.dahohelping_springboot.repository;

import com.dahohelping.dahohelping_springboot.entity.University;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {
    // Các phương thức xử lý dữ liệu của Univerisy

    @Query("SELECT u FROM University u WHERE u.name = ?1")
    University findByUniversityName(String name);

    @Query("SELECT u FROM University u WHERE u.id = ?1")
    University findUniversityById(Integer uniId);

    @Query("SELECT u FROM University u WHERE u.code = ?1")
    University findUniversityByCode(String code);

    @Modifying
    @Transactional
    @Query("DELETE FROM University u WHERE u.name = ?1")
    void deleteByUniversityName(String name);

    @Query("SELECT u FROM University u WHERE u.name LIKE %?1%")
    List<University> findByNameContaining(String keyword);

    //List<University> findAll();
    //<S extends University> S save(S entity);
    //long count();
    //boolean existsById(Integer id);
    //void deleteAll();

}

