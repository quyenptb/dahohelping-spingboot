package com.dahohelping.dahohelping_springboot.service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardResponse {
    Integer id;
    String title;
    String award;
    String text;
    LocalDateTime createdDate;
    String images;
    Boolean isReported;
    Boolean isAnswered;

    Integer userId;
    String username;
    String userAvatar;

    String dahohelpingName;
    String universityName;
    String universityCode;
    String facultyName;
    String majorName;
    String subjectName;
}