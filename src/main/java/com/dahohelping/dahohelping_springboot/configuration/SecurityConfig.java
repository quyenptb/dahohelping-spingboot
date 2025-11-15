package com.dahohelping.dahohelping_springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig { //Authentication of this webapp

    private final String[] PUBLIC_ENDPOINTS = {"api/upload/**","/users/**",
            "auth/login/**", "auth/logout", "auth/signin/**", "auth/introspect", "search/**", "cards/**", "universities/**",
            "faculties/**", "majors/**", "subjects/**", "badges/**"
    };

    @Autowired
    CustomJwtDecoder customJwtDecoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll() //dang-nhap,...
                .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS).permitAll()
                        //.requestMatchers(HttpMethod.GET, "/users").hasAuthority("SCOPE_ADMIN")
                        .anyRequest().authenticated());

        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder)) //authentication provider (nhà cung cấp
                //token authentication, in this case our webapp generate it (token)
        );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }


    @Bean
    PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder(10);
    }
}