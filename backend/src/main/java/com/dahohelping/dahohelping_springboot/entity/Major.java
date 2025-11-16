package com.dahohelping.dahohelping_springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "maj_id")
    Integer id;

    @NotEmpty
    @Size(max = 255)
    String name;

    @Lob
    @Column(name = "description")
    String desc;
    String illust;

    @OneToMany(mappedBy = "major", orphanRemoval = true)
    Set<Subject> subjects;

    @OneToMany(mappedBy = "major", orphanRemoval = true)
    Set<Card> cards;

    @ManyToOne
    @JoinColumn(name = "fac_id")
    Faculty faculty;

}