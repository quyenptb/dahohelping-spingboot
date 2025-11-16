package com.dahohelping.dahohelping_springboot.service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardFilterDto {
    Integer dahoId;
    Integer uniId;
    Integer falId;
    Integer majId;
    Integer subId;
}