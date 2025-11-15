package com.dahohelping.dahohelping_springboot.service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data //tự tạo getter, setter
@Builder //tạo nhanh instance ở các layer khác
@AllArgsConstructor //hàm tạo mọi đối số
@NoArgsConstructor //hàm tạo không đối số
@FieldDefaults(level = AccessLevel.PRIVATE) //tạo tầm vực mặc định là private
public class CommentCreationRequest {
    LocalDateTime createdDate;
    String images;
    String text;
    Integer userId;
    String username;
    Integer cardId;
    Integer parentCommentId;

}
