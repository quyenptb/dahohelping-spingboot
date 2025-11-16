package com.dahohelping.dahohelping_springboot.service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data //tự tạo getter, setter
@Builder //tạo nhanh instance ở các layer khác
@AllArgsConstructor //hàm tạo mọi đối số
@NoArgsConstructor //hàm tạo không đối số
@FieldDefaults(level = AccessLevel.PRIVATE) //tạo tầm vực mặc định là private
public class PermissionRequest {
    String name;
    String description;
}
