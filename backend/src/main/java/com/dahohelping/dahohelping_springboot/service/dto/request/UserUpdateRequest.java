package com.dahohelping.dahohelping_springboot.service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data //tự tạo getter, setter
@Builder //tạo nhanh instance ở các layer khác
@AllArgsConstructor //hàm tạo mọi đối số
@NoArgsConstructor //hàm tạo không đối số
@FieldDefaults (level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String username;
    String avatar;
    String fullName;
    String email;
    String hometown;
    String hobby;
    String bio;
}
