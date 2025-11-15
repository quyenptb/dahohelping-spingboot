package com.dahohelping.dahohelping_springboot.service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data //tự tạo getter, setter
@Builder //tạo nhanh instance ở các layer khác
@AllArgsConstructor //hàm tạo mọi đối số
@NoArgsConstructor //hàm tạo không đối số
@FieldDefaults(level = AccessLevel.PRIVATE) //tạo tầm vực mặc định là private
public class CardCreationRequest {
    String title;
    String award;
    LocalDateTime createdDate;
    String images;
    String text;
    Integer dahohelpingId;
    Integer universityId;
    Integer majorId;
    Integer facultyId;
    Integer userId;
    Integer subjectId;
}
