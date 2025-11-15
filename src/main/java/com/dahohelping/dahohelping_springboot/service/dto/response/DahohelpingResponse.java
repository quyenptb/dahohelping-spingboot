package com.dahohelping.dahohelping_springboot.service.dto.response;

import com.dahohelping.dahohelping_springboot.entity.Card;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Data //tự tạo getter, setter
@Builder //tạo nhanh instance ở các layer khác
@AllArgsConstructor //hàm tạo mọi đối số
@NoArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class DahohelpingResponse {
    Integer id;
    @NotEmpty
    @Size(max = 255)
    String name;
    String desc;
    String illust;

}
