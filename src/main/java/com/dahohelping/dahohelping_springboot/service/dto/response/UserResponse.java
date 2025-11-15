package com.dahohelping.dahohelping_springboot.service.dto.response;

import com.dahohelping.dahohelping_springboot.entity.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data //tự tạo getter, setter
@Builder //tạo nhanh instance ở các layer khác
@AllArgsConstructor //hàm tạo mọi đối số
@NoArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class UserResponse {
    Integer id;
    String username;
    String avatar;
    String fullName;
    Integer uniId;
    Integer facId;
    Integer majId;
    String email;
    String hometown;
    Integer score;
    String hobby;
    String bio;
    Set<Role> roles;
}
