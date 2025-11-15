package com.dahohelping.dahohelping_springboot.service;

import com.dahohelping.dahohelping_springboot.entity.Role;
import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.exception.AppException;
import com.dahohelping.dahohelping_springboot.exception.ErrorCode;
import com.dahohelping.dahohelping_springboot.mapper.UserMapper;
import com.dahohelping.dahohelping_springboot.repository.UserRepository;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<UserResponse> getRanking(int limit) {
        PageRequest top = PageRequest.of(0, limit);
        List<User> topUsers = userRepository.getRanking(top);
        return topUsers.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }


    public UserResponse getMyInfo() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        //never String username = auth.getName() here -> if auth is null, then NPE will be thrown before AppException is catched
        if (auth == null || auth.getName() == null) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        String username = auth.getName();
        User user = userRepository.findByUsername(username);
        if (user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        return userMapper.toUserResponse(user);
    }


    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void updateScoreById(Integer userId, Integer deltaScore) {
        User user = userRepository._findById(userId);
        if (user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        int newScore = user.getScore() - deltaScore;
        user.setScore(Math.max(newScore, 0)); // tránh âm
        userRepository.save(user);
    }


    public Integer getScoreById(Integer userId) {
        User user = userRepository._findById(userId);
        if (user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        // TODO: kiểm tra quyền nếu cần
        return user.getScore();
    }


    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }


    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        return userMapper.toUserResponse(user);
    }


    public UserResponse getUserById(Integer userId) {
        User user = userRepository._findById(userId);
        if (user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        return userMapper.toUserResponse(user);
    }


    public List<UserResponse> getUsersByUsernameContaining(String keyword) {
        return userRepository.findByUsernameContaining(keyword)
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }


    public ResponseEntity<ApiResponse<UserResponse>> createUser(UserCreationRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null)
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = new Role();
        role.setName(com.dahohelping.dahohelping_springboot.roles.Role.USER.name());
        user.setRoles(Set.of(role));

        User savedUser = userRepository.save(user);
        UserResponse savedResponse = userMapper.toUserResponse(savedUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(savedResponse.getUsername())
                .toUri();

        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(savedResponse);

        return ResponseEntity.created(location).body(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> deleteUser(Integer userId) {
        User user = userRepository._findById(userId);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        userRepository.delete(user);
        return ResponseEntity.ok("User deleted successfully");
    }

    public ResponseEntity<?> deleteMe(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        userRepository.delete(user);
        return ResponseEntity.ok("User deleted successfully");
    }


    @Transactional
    public void updateUser(Map<String, Object> updates, Integer userId) {
        User user = userRepository._findById(userId);
        if (user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);

        updates.forEach((key, value) -> {
            switch (key) {
                case "username": user.setUsername((String) value); break;
                case "password": 
                    user.setPassword(passwordEncoder.encode((String) value)); 
                    break;
                case "avatar": user.setAvatar((String) value); break;
                case "fullName": user.setFullName((String) value); break;
                case "email": user.setEmail((String) value); break;
                case "hometown": user.setHometown((String) value); break;
                case "hobby": user.setHobby((String) value); break;
                case "bio": user.setBio((String) value); break;
                default: break;
            }
        });

        userRepository.save(user);
    }
}
