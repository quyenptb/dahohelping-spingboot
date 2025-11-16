package com.dahohelping.dahohelping_springboot.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data //tự tạo getter, setter
@Builder //tạo nhanh instance ở các layer khác
@AllArgsConstructor //hàm tạo mọi đối số
@NoArgsConstructor //hàm tạo không đối số
@FieldDefaults(level = AccessLevel.PRIVATE) //tạo tầm vực mặc định là private
public class UserCreationRequest {
    String username;
    @Size(min = 7, message="Mật khẩu phải có ít nhất 7 kí tự")
    String password;
    String avatar;
    String fullName;
    String uniId;
    String facId;
    String majId;
    String email;
    String hometown;
    String hobby;
    String bio;
    Integer googleId;
}
