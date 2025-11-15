package com.dahohelping.dahohelping_springboot.service;

import com.dahohelping.dahohelping_springboot.entity.Card;
import com.dahohelping.dahohelping_springboot.entity.Role;
import com.dahohelping.dahohelping_springboot.entity.User;

import java.security.Principal;
import java.util.*;

import com.dahohelping.dahohelping_springboot.exception.AppException;
import com.dahohelping.dahohelping_springboot.exception.ErrorCode;
import com.dahohelping.dahohelping_springboot.mapper.UserMapper;
import com.dahohelping.dahohelping_springboot.repository.UserRepository;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserUpdateRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.CardResponse;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper     userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder; //for encoding password purpose

    public Set<UserResponse> getRanking(Integer number) {
        PageRequest top = PageRequest.of(0, number);
        List<User> topUsers = userRepository.getRanking(top);
        return   topUsers.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toSet());
    }

    public UserResponse getMyInfo() {
        var context =  SecurityContextHolder.getContext(); //store all of "this user" info
        if (userRepository.findByUsername(context.getAuthentication().getName()) != null) { //check name of this user
            return userMapper.toUserResponse(userRepository.findByUsername(context.getAuthentication().getName()));
        }
        else throw new RuntimeException("User's not existed");
    }

    @Transactional
    public void updateScoreById(Integer newScore, Integer userId) {
        User user = userRepository._findById(userId);
        user.setScore(user.getScore()-newScore);
        userRepository.save(user);
        return;
    }

    //pass
    public Integer getScoreById(Integer userId) {
        var context =  SecurityContextHolder.getContext(); //store all of "this user" info
        if (userRepository._findById(userId) != null) { //check name of this user
            User user =   userRepository._findById(userId);
            return user.getScore();
        }
        else throw new RuntimeException("User's not existed");
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<UserResponse> getUsers() {
        List<User> list = userRepository.findAll();
        List<UserResponse> _list = new ArrayList<>(List.of());
        for (User i : list ) {
            _list.add(userMapper.toUserResponse(i));
        }
        return _list;
    }

    //pass
    public UserResponse getUserByUsername(String username) {
        if (userRepository.findByUsername(username) != null) {
            return userMapper.toUserResponse(userRepository.findByUsername(username));
        }
        else throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }

    //pass
    public UserResponse getUserById(Integer userId) {
        if (userRepository._findById(userId) != null) {
            return userMapper.toUserResponse(userRepository._findById(userId));
        }
        else throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }


    public Set<UserResponse> getUsersByUsernameContaining(String keyword) {
        List<User> users = userRepository.findByUsernameContaining(keyword);
        return users.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toSet());
    }

    //Sign up
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserCreationRequest request) {

        ApiResponse<UserResponse> apiResponse = new ApiResponse<UserResponse>();
        if (userRepository.findByUsername(request.getUsername()) != null)
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> role = new HashSet<>(Set.of());
        Role _role = new Role();
        _role.setName(com.dahohelping.dahohelping_springboot.roles.Role.USER.name());
        role.add(_role);
        user.setRoles(role);
        //System.out.println(Role.USER.name());

        UserResponse savedUser = userMapper.toUserResponse(userRepository.save(user));
        //savedUser.setRoles("USER");

        URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{username}")
                    .buildAndExpand(savedUser.getUsername())
                    .toUri();

        apiResponse.setResult(savedUser);

        return ResponseEntity.created(location).body(apiResponse);
        }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> deleteUser(Integer userId){
        User user = userRepository._findById(userId);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        else {
            userRepository.delete(user);
            return ResponseEntity.ok("User deleted successfully");
        }
    }

    public ResponseEntity<?> deleteMe(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.delete(user);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @Transactional
    public void updateUser(Map<String, Object> updates, Integer userId) {
        User user = userRepository._findById(userId);
        // Apply updates to the user
        updates.forEach((key, value) -> {
            switch (key) {
                case "username":
                    user.setUsername((String) value);
                    break;
                case "password":
                    user.setPassword((String) value);
                    break;
                case "avatar":
                    user.setAvatar((String) value);
                    break;
                case "fullName":
                    user.setFullName((String) value);
                    break;
                case "email":
                    user.setEmail((String) value);
                    break;
                case "hometown":
                    user.setHometown((String) value);
                    break;
                case "score":
                    user.setScore((Integer) value);
                    break;
                case "hobby":
                    user.setHobby((String) value);
                    break;
                case "bio":
                    user.setBio((String) value);
                    break;
                default:
                    // Handle unknown properties if needed
                    break;
            }
        });

        // Save the updated user
        userRepository.save(user);

        // Convert to UserResponse and return
        return;
    }
}

