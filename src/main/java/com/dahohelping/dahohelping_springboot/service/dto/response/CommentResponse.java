package com.dahohelping.dahohelping_springboot.service.dto.response;

import com.dahohelping.dahohelping_springboot.entity.Card;
import com.dahohelping.dahohelping_springboot.entity.Comment;
import com.dahohelping.dahohelping_springboot.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    Integer id;
    Integer parentCommentId;
    String text;
    LocalDateTime createdDate;
    Integer cardId;
    Integer userId;
    Integer username;
    String images;


}

