package com.dahohelping.dahohelping_springboot.service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults (level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    Integer userId;
    String token;
    boolean isAuthenticated;
}
