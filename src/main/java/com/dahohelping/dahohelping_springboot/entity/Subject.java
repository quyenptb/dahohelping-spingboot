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
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "sub_id")
    Integer id;

    @NotEmpty
    @Size(max = 255)
    String name;
    String icon;

    @Column(name = "description")
    @Lob
    String desc;
    String illust;

    @ManyToOne
    @JoinColumn(name = "maj_id")
    Major major;

    @OneToMany(mappedBy =  "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Card> cards;

}