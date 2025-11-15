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
        return userService.createUser(request);
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

    @PatchMapping("/score/{userId}")
    public void updateScoreById(@RequestBody Integer score, @PathVariable("userId") Integer userId) {
        userService.updateScoreById(score, userId);
        return;
    }

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE})
@PatchMapping("/update/{userId}")
public void updateUser(@RequestBody Map<String, Object> updates, @PathVariable("userId") Integer userId) {
    userService.updateUser(updates, userId);
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

