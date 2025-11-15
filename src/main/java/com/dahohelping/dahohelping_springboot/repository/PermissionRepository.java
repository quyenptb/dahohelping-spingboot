package com.dahohelping.dahohelping_springboot.repository;

import com.dahohelping.dahohelping_springboot.entity.Permission;
import com.dahohelping.dahohelping_springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

}
