package com.dahohelping.dahohelping_springboot.service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationRequest {
    Integer id;
    String title;
    String content;
    LocalDateTime createdDate;
    String image;
    Integer ownerId; // Sử dụng một tên rõ ràng hơn cho quan hệ
    Integer receiverId; // Sử dụng một tên rõ ràng hơn cho quan hệ
}

