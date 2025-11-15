package com.dahohelping.dahohelping_springboot.service.dto.response;

import com.dahohelping.dahohelping_springboot.entity.Faculty;
import com.dahohelping.dahohelping_springboot.entity.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MajorResponse {
    Integer id;
    @NotEmpty
    @Size(max = 255)
    String name;
    String desc;
    String illust;
    String facId;
}