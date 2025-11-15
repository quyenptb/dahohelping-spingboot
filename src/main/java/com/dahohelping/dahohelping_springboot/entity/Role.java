package com.dahohelping.dahohelping_springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Set;

@EntityScan
@Entity
@Data //tự tạo getter, setter
@Builder //tạo nhanh instance ở các layer khác
@AllArgsConstructor //hàm tạo mọi đối số
@NoArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class Role {
    @Id
    String name;

    @Lob
    String description;
    @ManyToMany
    Set<Permission> permissions; //new Role - Permission table
}

