package com.dahohelping.dahohelping_springboot.service.dto.response;

import com.dahohelping.dahohelping_springboot.entity.University;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BadgeResponse {
    Integer id;
    String name;
    String scoreTarget;
    String icon;
    String desc; // Sử dụng "description" thay vì "desc"
    Integer awardeeId; // Sử dụng một tên rõ ràng hơn cho mối quan hệ với User
    Integer affiliatedUniversityId; // Sử dụng một tên rõ ràng hơn cho mối quan hệ với University
}

