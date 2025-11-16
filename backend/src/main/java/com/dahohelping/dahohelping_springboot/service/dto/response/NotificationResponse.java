package com.dahohelping.dahohelping_springboot.service.dto.response;

import com.dahohelping.dahohelping_springboot.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationResponse {
    Integer id;
    String title;
    String content;
    LocalDateTime createdDate;
    String image;
    Integer ownerId; // Sử dụng một tên rõ ràng hơn cho quan hệ
    Integer receiverId; // Sử dụng một tên rõ ràng hơn cho quan hệ
}

