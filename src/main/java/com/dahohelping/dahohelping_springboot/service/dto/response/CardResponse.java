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
    Integer dahohelpingId; //tính
    Integer universityId; //tính
    Integer majorId; //tính
    Integer facultyId; //tính
    Integer userId; //động
    Integer subjectId; //tính
}

