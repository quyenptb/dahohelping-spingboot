package com.dahohelping.dahohelping_springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    Integer id;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "name")
    String name;

    @Column(name = "score_target")
    String scoreTarget;

    @Column(name = "icon")
    String icon;

    @Lob
    @Column(name = "description")
    String desc;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User awardee;

    @ManyToOne
    @JoinColumn(name = "uni_id")
    University affiliatedUniversity;
}