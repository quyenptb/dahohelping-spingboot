package com.dahohelping.dahohelping_springboot.controller;

import com.dahohelping.dahohelping_springboot.service.AuthenticationService;
import com.dahohelping.dahohelping_springboot.service.dto.request.*;
import com.dahohelping.dahohelping_springboot.service.dto.response.AuthenticationResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

//1. auth bình thường
//2. auth with google
//3. check xem token có còn valid hay không?

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    AuthenticationResponse loginAuthentication(@RequestBody AuthenticationRequest request) throws KeyLengthException {
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<AuthenticationResponse>();

        apiResponse.setResult(authenticationService.authenticate(request));

        return authenticationService.authenticate(request);
    }

    @PostMapping("/loginWithGoogle")
    ApiResponse<AuthenticationResponse>loginAuthenticationWithGoogle(@RequestBody GoogleAuthenticationRequest request) throws Exception {
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<AuthenticationResponse>();

        apiResponse.setResult(authenticationService.authenticateGoogle(request));

        return apiResponse;
    }

    @PostMapping("/refresh")
    AuthenticationResponse authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return result;
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
        authenticationService.logout(request);
        ApiResponse apiResponse = new ApiResponse<Void>();
        return apiResponse;
    }


    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> loginAuthentication(@RequestBody IntrospectRequest request) throws JOSEException, ParseException {
        ApiResponse<IntrospectResponse> apiResponse = new ApiResponse<IntrospectResponse>();

        apiResponse.setResult(authenticationService.introspect(request));

        return apiResponse;
    }

}



