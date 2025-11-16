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
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    Integer id;

    @NotEmpty
    @Size(max = 255)
    String title;

    @Column(name = "award_text")
    @Size(max = 255)
    String awardText;

    @Column(name = "award_score")
    Integer awardScore;

    @Lob
    @Column(name = "text_content")
    String text;

    @Column(name = "created_date", updatable = false, nullable = false)
    @CreationTimestamp
    LocalDateTime createdDate;

    @Size(max = 255)
    String images;

    @Column(name = "is_reported")
    Boolean isReported;

    @Column(name = "is_answered")
    Boolean isAnswered;

    @ManyToOne
    @JoinColumn(name = "daho_id")
    Dahohelping dahohelping;

    @ManyToOne
    @JoinColumn(name = "uni_id")
    University university;

    @ManyToOne //quan he card - user la one - to - many
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "sub_id")
    Subject subject;

    @ManyToOne
    @JoinColumn(name = "maj_id")
    Major major;

    @ManyToOne
    @JoinColumn(name = "fac_id")
    Faculty faculty;

}