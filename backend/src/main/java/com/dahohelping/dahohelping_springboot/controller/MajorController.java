package com.dahohelping.dahohelping_springboot.controller;

import com.dahohelping.dahohelping_springboot.service.MajorService;
import com.dahohelping.dahohelping_springboot.service.dto.response.MajorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/majors")
public class MajorController {

    @Autowired
    private MajorService majorService;

    @GetMapping("/myMajor")
    public MajorResponse getMyMajor() {
        return majorService.getMyMajor();
    }

    @GetMapping("/{id}")
    public MajorResponse getMajorById(@PathVariable Integer id) {
        return majorService.getMajorById(id);
    }

    @GetMapping("/nameContaining/{s}")
    public List<MajorResponse> getMajorByNameContaining(@PathVariable String s) {
        return majorService.getMajorByNameContaining(s);
    }

    @GetMapping("/name/{name}")
    public MajorResponse getMajorByName(@PathVariable String name) {
        return majorService.getMajorByName(name);
    }

    @GetMapping("/all")
    public List<MajorResponse> getAllMajors() {
        return majorService.getAllMajors();
    }

    @GetMapping("/byUsername/{username}")
    public MajorResponse getMajorByUsername(@PathVariable String username) {
        return majorService.getMajorByUsername(username);
    }

    @GetMapping("/byFaculty/{facultyId}")
    public List<MajorResponse> getMajorsByFacultyId(@PathVariable Integer facultyId) {
        return majorService.getMajorsByFacultyId(facultyId);
    }

    @GetMapping("/byUniversityId/{universityId}")
    public List<MajorResponse> getMajorsByUniversityId(@PathVariable Integer universityId) {
        return majorService.getMajorsByUniversityId(universityId);
    }

    @GetMapping("/byUniversityCode/{code}")
    public List<MajorResponse> getMajorsByUniversityCode(@PathVariable String code) {
        return majorService.getMajorsByUniversityCode(code);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMajor(@PathVariable Integer id) {
        return majorService.deleteMajor(id);
    }
}
