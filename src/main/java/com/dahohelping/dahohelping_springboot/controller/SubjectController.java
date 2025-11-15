package com.dahohelping.dahohelping_springboot.controller;

import com.dahohelping.dahohelping_springboot.service.SubjectService;
import com.dahohelping.dahohelping_springboot.service.dto.response.SubjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/{id}")
    public SubjectResponse getSubjectById(@PathVariable Integer id) {
        return subjectService.getSubjectById(id);
    }

    @GetMapping("/nameContaining/{s}")
    public Set<SubjectResponse> getSubjectByNameContaining(@PathVariable String s) {
        return subjectService.getSubjectByNameContaining(s);
    }

    @GetMapping("/name/{name}")
    public SubjectResponse getSubjectByName(@PathVariable String name) {
        return subjectService.getSubjectByName(name);
    }

    @GetMapping("/all")
    public List<SubjectResponse> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/byMajor/{majorId}")
    public Set<SubjectResponse> getSubjectsByMajorId(@PathVariable Integer majorId) {
        return subjectService.getSubjectsByMajorId(majorId);
    }

    @GetMapping("/byFaculty/{facultyId}")
    public Set<SubjectResponse> getSubjectsByFacultyId(@PathVariable Integer facultyId) {
        return subjectService.getSubjectsByFacultyId(facultyId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable Integer id) {
        return subjectService.deleteSubject(id);
    }
}
