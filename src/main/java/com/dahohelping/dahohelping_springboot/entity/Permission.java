package com.dahohelping.dahohelping_springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
@Entity
@Data //tự tạo getter, setter
@Builder //tạo nhanh instance ở các layer khác
@AllArgsConstructor //hàm tạo mọi đối số
@NoArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class Permission {
    @Id
    String name;

    @Lob
    String description;
}
