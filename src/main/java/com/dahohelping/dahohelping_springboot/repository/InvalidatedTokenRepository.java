package com.dahohelping.dahohelping_springboot.repository;

import com.dahohelping.dahohelping_springboot.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}