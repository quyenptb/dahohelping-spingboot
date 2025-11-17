package com.dahohelping.dahohelping_springboot.entity;

import com.dahohelping.dahohelping_springboot.utils.EncryptionUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDate;
import java.util.Set;

@EntityScan
@Entity
@Data //tự tạo getter, setter
@Builder //tạo nhanh instance ở các layer khác
@AllArgsConstructor //hàm tạo mọi đối số
@NoArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Integer id;
    String username;
    @Size(min = 7, message="Mật khẩu phải có ít nhất 7 kí tự")
    @NotEmpty
    String password;
    String avatar;
    String fullName;
    String email;
    String hometown;
    Integer score; //điểm khởi tạo
    String hobby;
    @JsonIgnore
    @Column(name = "google_id")
    String googleId;

    @Lob
    String bio;
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"), // <-- Dòng này chỉ định tên cột là "user_id"
            inverseJoinColumns = @JoinColumn(name = "role_name")
    )
    Set<Role> roles;
    @ManyToOne
    @JoinColumn(name = "uni_id")
    University university;
    @ManyToOne
    @JoinColumn(name = "fac_id")
    Faculty faculty;
    @ManyToOne
    @JoinColumn(name = "maj_id")
    Major major;

    @OneToMany(mappedBy = "user")
    Set <Card> cards;

    @OneToMany(mappedBy = "awardee")
    Set <Badge> badges;

    @OneToMany(mappedBy = "owner")
    Set <Notification> notificationsOwner;

    @OneToMany(mappedBy = "receiver")
    Set <Notification> notificationsReceiver;

    public void setGoogleId(String googleId) {
        try {
            this.googleId = EncryptionUtils.encrypt(googleId);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting Google ID", e);
        }
    }

    public String getGoogleId() {
        try {
            return EncryptionUtils.decrypt(this.googleId);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting Google ID", e);
        }
    }

}

