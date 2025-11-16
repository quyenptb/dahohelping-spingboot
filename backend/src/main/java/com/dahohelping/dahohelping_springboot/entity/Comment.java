package com.dahohelping.dahohelping_springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "USERCOMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "comment_id")
    Integer id;


    @Column(name = "text_content")
    String text;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Comment> replies;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    Comment parentComment;

    Integer cardId;
    Integer userId;
    String username;
    String images;

    @Column(name = "created_date", updatable = false, nullable = false)
    @CreationTimestamp
    LocalDateTime createdDate;

}