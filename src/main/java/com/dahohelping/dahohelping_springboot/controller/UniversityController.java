package com.dahohelping.dahohelping_springboot.controller;

import com.dahohelping.dahohelping_springboot.service.UniversityService;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.UniversityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping("/universities")

@RestController
public class UniversityController {

    @Autowired
    private UniversityService universityService;

    @GetMapping("/myUniversity")
    public UniversityResponse getMyUniversity() {
        return universityService.getMyUniversity();
    }

    @GetMapping("/{id}")
    public UniversityResponse getUniversityById(@PathVariable Integer id) {
        return universityService.getUniversityById(id);
    }

    @GetMapping("/code/{code}")
    public UniversityResponse getUniversityByCode(@PathVariable String code) {
        return universityService.getUniversityByCode(code);
    }

    @GetMapping("/nameContaining/{s}")
    public Set<UniversityResponse> getUniversityByNameContaining(@PathVariable String s) {
        return universityService.getUniversityByNameContaining(s);
    }

    @GetMapping("/name/{name}")
    public UniversityResponse getUniversityByName(@PathVariable String name) {
        return universityService.getUniversityByName(name);
    }

    @GetMapping("/all")
    public List<UniversityResponse> getAllUniversity() {
        return universityService.getAllUniversity();
    }

    @GetMapping("/byUsername/{username}")
    public UniversityResponse getUniversityByUsername(@PathVariable String username) {
        return universityService.getUniversityByUsername(username);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUniversity(@PathVariable Integer id) {
        return universityService.deleteUniversity(id);
    }
}
