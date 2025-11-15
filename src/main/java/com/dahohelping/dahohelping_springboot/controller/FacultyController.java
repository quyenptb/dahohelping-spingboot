package com.dahohelping.dahohelping_springboot.controller;

import com.dahohelping.dahohelping_springboot.service.FacultyService;
import com.dahohelping.dahohelping_springboot.service.dto.response.FacultyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/myFaculty")
    public FacultyResponse getMyFaculty() {
        return facultyService.getMyFaculty();
    }

    @GetMapping("/{id}")
    public FacultyResponse getFacultyById(@PathVariable Integer id) {
        return facultyService.getFacultyById(id);
    }

    @GetMapping("/nameContaining/{keyword}")
    public List<FacultyResponse> getFacultyByNameContaining(@PathVariable String keyword) {
        return facultyService.getFacultyByNameContaining(keyword);
    }

    @GetMapping("/name/{name}")
    public FacultyResponse getFacultyByName(@PathVariable String name) {
        return facultyService.getFacultyByName(name);
    }

    @GetMapping("/universityCode/{code}")
    public List<FacultyResponse> getFacultyByUniversityCode(@PathVariable String code) {
        return facultyService.getFacultyByUniversityCode(code);
    }

    @GetMapping("/universityId/{id}")
    public List<FacultyResponse> getFacultyByUniversityId(@PathVariable Integer id) {
        return facultyService.getFacultyByUniversityId(id);
    }

    @GetMapping("/username/{username}")
    public FacultyResponse getFacultyByUsername(@PathVariable String username) {
        return facultyService.getFacultyByUsername(username);
    }

    @GetMapping
    public List<FacultyResponse> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFaculty(@PathVariable Integer id) {
        return facultyService.deleteFaculty(id);
    }
}
