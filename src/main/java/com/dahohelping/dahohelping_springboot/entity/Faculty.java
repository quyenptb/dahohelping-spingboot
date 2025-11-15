package com.dahohelping.dahohelping_springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fac_id")
    Integer id;

    @NotEmpty
    @Size(max = 255)
    String name;

    String code;

    @OneToMany(mappedBy = "faculty", orphanRemoval = true)
    Set<Major> majors;  // Quan hệ One-to-Many với Major

    @OneToMany(mappedBy = "faculty", orphanRemoval = true)
    Set<User> users;  // Quan hệ One-to-Many với Major

    @ManyToOne
    @JoinColumn(name = "uni_id")
    University university;

    @OneToMany(mappedBy =  "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Card> cards;

}
