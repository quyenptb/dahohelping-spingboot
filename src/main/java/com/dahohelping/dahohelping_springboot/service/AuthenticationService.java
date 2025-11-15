package com.dahohelping.dahohelping_springboot.service;

import com.dahohelping.dahohelping_springboot.entity.InvalidatedToken;
import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.exception.AppException;
import com.dahohelping.dahohelping_springboot.exception.ErrorCode;
import com.dahohelping.dahohelping_springboot.repository.InvalidatedTokenRepository;
import com.dahohelping.dahohelping_springboot.repository.UserRepository;
import com.dahohelping.dahohelping_springboot.service.dto.request.*;
import com.dahohelping.dahohelping_springboot.service.dto.response.AuthenticationResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.IntrospectResponse;
import com.dahohelping.dahohelping_springboot.utils.EncryptionUtils;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jca.JCAContext;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.*;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor

public class AuthenticationService {
    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepository;
    @Autowired
    UserRepository userRepository;
    @NonFinal
    protected static final String SIGNER_KEY = "IHHdeH3i4T0FXlwXUmwl9kfOCv8Exr+WMaUPMSACB5g=";

    //Kiểm tra Token có hợp lệ hay không
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        boolean checkVerified = signedJWT.verify(jwsVerifier); //check token co chinh xac khong

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime(); //lay ngay het han cua token

        return IntrospectResponse.builder()
                .valid((expirationTime.after(new Date()) && checkVerified))
                .build();
    }
    //for login with google
    public AuthenticationResponse authenticateGoogle(GoogleAuthenticationRequest request) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String googleId = request.getGoogleIdToken();
        User user = userRepository.findByGoogleId(EncryptionUtils.encrypt(googleId));
        boolean isAuthenticated = request.getGoogleIdToken().matches(user.getGoogleId());

        if (!isAuthenticated) {
            throw new RuntimeException("Unauthorized");
        }
        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();
    }

    //for login
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws KeyLengthException {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = userRepository.findByUsername(request.getUsername());
        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isAuthenticated) {
            throw new RuntimeException("Unauthorized");
        }
        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .userId(user.getId())
                .isAuthenticated(true)
                .build();
    }

    private String generateToken(User user) throws KeyLengthException { //tạo token

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("Dahohelping")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", user.getRoles()) //Các claim sẽ thêm vào sau này
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jwsObject.serialize();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID(); //lấy claim liên quan đến jwtID
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime(); //lấy claim liên quan đến ngày hết hạn

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                            .id(jit)
                            .expiryTime(expiryTime)
                            .build();

            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException exception){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user =
                userRepository.findByUsername(username);

        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).isAuthenticated(true).build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                .toInstant().plus(86400000, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }




}

