package com.dahohelping.dahohelping_springboot.service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoogleAuthenticationRequest {
    @NotEmpty
    String googleIdToken; // Token xác thực từ Google
}
