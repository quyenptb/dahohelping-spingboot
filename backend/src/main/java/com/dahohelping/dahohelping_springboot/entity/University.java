package com.dahohelping.dahohelping_springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "uni_id")
    Integer id;
    @NotEmpty
    @Size(max = 255)
    String name;
    @NotEmpty
    @Size(max = 10)
    String code;
    @NotEmpty
    @Size(max = 255)
    String icon;
    String src1;
    String src2;
    String src3;

    @Lob
    @Column(name = "description")
    String desc;
    Integer drl;

    @OneToMany(mappedBy = "university", orphanRemoval = true)
    Set<Faculty> faculties;

    @OneToMany(mappedBy = "university", orphanRemoval = true)
    Set<User> users;

    @OneToMany(mappedBy = "affiliatedUniversity", orphanRemoval = true)
    Set<Badge> badges;

    @OneToMany(mappedBy = "university", orphanRemoval = true)
    Set<Card> cards;
}