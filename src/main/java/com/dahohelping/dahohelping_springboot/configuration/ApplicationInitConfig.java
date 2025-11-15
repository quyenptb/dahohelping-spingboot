package com.dahohelping.dahohelping_springboot.configuration;

import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.repository.UserRepository;
import com.dahohelping.dahohelping_springboot.roles.Role;
import com.dahohelping.dahohelping_springboot.service.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration public class ApplicationInitConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) { //run when webapp start
        return args -> {
            var roles = new HashSet<String>();
            roles.add(Role.ADMIN.name());
            if (userRepository.findByUsername("admin") == null) {
                User user = User.builder()
                        .username("admin")
                        //.roles()
                        .password(passwordEncoder.encode("admin"))
                        .build();
                userRepository.save(user);
                System.out.println("Admin has been created once, password is admin");
            }

        };
    }

}
