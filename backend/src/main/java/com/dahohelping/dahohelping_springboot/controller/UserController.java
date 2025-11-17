package com.dahohelping.dahohelping_springboot.controller;

import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.service.dto.response.UserResponse;
import com.dahohelping.dahohelping_springboot.service.UserService;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserUpdateRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    //pass
    @PostMapping("/signin")
   ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody @Valid UserCreationRequest request) {
        UserResponse savedUser = userService.createUser(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(savedUser.getUsername())
                .toUri();

        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(savedUser);

        return ResponseEntity.created(location).body(response);
    }

    //you do not have permission
    @GetMapping
    List<ApiResponse<UserResponse>> getUser() {
        List<ApiResponse<UserResponse>> _list = new ArrayList<>(List.of());

        for (UserResponse i : userService.getUsers()) {
            ApiResponse<UserResponse> apiResponse = new ApiResponse<UserResponse>();
            apiResponse.setResult(i);
            _list.add(apiResponse);
        }
        return _list;
    }

    //pass
    @GetMapping("/username/{username}")
    public UserResponse getUserByUsername(@PathVariable("username") String username) {

        UserResponse user = userService.getUserByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with username: " + username);
        }
    }

    //pass
    @GetMapping("/id/{id}")
    public UserResponse getUserById(@PathVariable("id") Integer id) {
        UserResponse user = userService.getUserById(id);
        if (user != null) {
            return user;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: "+id);
        }
    }

    //pass
    @GetMapping("/myInfo")
    UserResponse getMyInfo() {
        return userService.getMyInfo();
    }

    //pass
    @GetMapping("/ranking/top/{number}")
    public List<UserResponse> getRanking(@PathVariable("number") Integer number) {
        return userService.getRanking(number);
    }

    @GetMapping("/score/{id}")
    public Integer getScoreById(@PathVariable("id") Integer userId) {
        return userService.getScoreById(userId);
    }

    /*
    @PatchMapping("/score/{userId}")
    public void updateScoreById(@RequestBody Integer score, @PathVariable("userId") Integer userId) {
        userService.updateScoreById(score, userId);
        return;
    } */

/*
String username;
    String avatar;
    String fullName;
    String email;
    String hometown;
    String hobby;
    String bio;
 */

@PatchMapping("/me")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void updateMyProfile(@RequestBody Map<String, Object> updates, @PathVariable("userId") Integer userId) {
    UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
            .username((String) updates.get("username"))
            .avatar((String) updates.get("avatar"))
            .fullName((String) updates.get("fullName"))
            .email((String) updates.get("email"))
            .hometown((String) updates.get("hometown"))
            .hobby((String) updates.get("hobby"))
            .bio((String) updates.get("bio"))
            .build();
    userService.updateMyProfile(userUpdateRequest);
}

    @DeleteMapping("/delete/me")
    public ResponseEntity<?> deleteMe(Principal principal) {
        return userService.deleteMe(principal);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteById(@PathVariable Integer userId) {
       return userService.deleteUser(userId);
    }
}

