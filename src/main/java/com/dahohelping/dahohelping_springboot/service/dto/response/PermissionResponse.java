package com.dahohelping.dahohelping_springboot.service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data //tự tạo getter, setter
@Builder //tạo nhanh instance ở các layer khác
@AllArgsConstructor //hàm tạo mọi đối số
@NoArgsConstructor //hàm tạo không đối số
@FieldDefaults(level = AccessLevel.PRIVATE) //tạo tầm vực mặc định là private
public class PermissionResponse {
    String name;
    String description;
}
