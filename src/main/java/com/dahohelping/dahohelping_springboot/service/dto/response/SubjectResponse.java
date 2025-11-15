package com.dahohelping.dahohelping_springboot.service.dto.response;

import com.dahohelping.dahohelping_springboot.entity.Major;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data //tự tạo getter, setter
@Builder //tạo nhanh instance ở các layer khác
@AllArgsConstructor //hàm tạo mọi đối số
@NoArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class SubjectResponse {
    Integer id;
    String name;
    String icon;
    String desc;
    String illust;
    String majorId;
}
