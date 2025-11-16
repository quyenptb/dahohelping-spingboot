package com.dahohelping.dahohelping_springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noti_id")
    Integer id;

    @NotEmpty
    @Size(max = 255)
    String title;

    @Size(max = 255)
    String content;

    @Column(name = "created_date")
    LocalDateTime createdDate;

    String image;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    User owner; // Sử dụng một tên rõ ràng hơn cho quan hệ

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    User receiver; // Sử dụng một tên rõ ràng hơn cho quan hệ

    // Thiết lập cascade và orphanRemoval tùy thuộc vào yêu cầu của ứng dụng
    // @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    // Set<NotificationDetail> notificationDetails;
}
