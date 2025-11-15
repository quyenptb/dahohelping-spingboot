package com.dahohelping.dahohelping_springboot.service;

import com.dahohelping.dahohelping_springboot.entity.Faculty;
import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.exception.AppException;
import com.dahohelping.dahohelping_springboot.exception.ErrorCode;
import com.dahohelping.dahohelping_springboot.mapper.CustomMapper;
import com.dahohelping.dahohelping_springboot.repository.FacultyRepository;
import com.dahohelping.dahohelping_springboot.repository.UserRepository;
import com.dahohelping.dahohelping_springboot.service.dto.response.FacultyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; //FOR NULL RESPONSE
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private CustomMapper customMapper;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public FacultyResponse getMyFaculty() {
        User user = userRepository.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()
        );

        if (user != null && user.getFaculty() != null) {
            return customMapper.toFacultyResponse(user.getFaculty());
        } else {
            throw new RuntimeException("Faculty or User does not exist");
        }
    }

    public FacultyResponse getFacultyById(Integer id) {
        Faculty faculty = facultyRepository._findById(id);
        if (faculty != null) {
            return customMapper.toFacultyResponse(faculty);
        } else {
            throw new AppException(ErrorCode.FACULTY_NOT_EXISTED);
        }
    }

    public Set<FacultyResponse> getFacultyByNameContaining(String s) {
        List<Faculty> faculties = facultyRepository.findByNameContaining(s);
        return faculties.stream()
                .map(customMapper::toFacultyResponse)
                .collect(Collectors.toSet());
    }

    public FacultyResponse getFacultyByName(String name) {
        Faculty faculty = facultyRepository.findByName(name);
        if (faculty != null) {
            return customMapper.toFacultyResponse(faculty);
        } else {
            throw new AppException(ErrorCode.FACULTY_NOT_EXISTED);
        }
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<FacultyResponse> getAllFaculties() {
        List<Faculty> faculties = facultyRepository.findAll();
        return faculties.stream()
                .map(customMapper::toFacultyResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public FacultyResponse getFacultyByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getFaculty() != null) {
            return customMapper.toFacultyResponse(user.getFaculty());
        } else {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    }

    public Set<FacultyResponse> getFacultyByUniversityCode(String code) {
        List<Faculty> faculties = facultyRepository.findByUniversityCode(code);
        return faculties.stream()
                .map(customMapper::toFacultyResponse)
                .collect(Collectors.toSet());
    }

    public Set<FacultyResponse> getFacultyByUniversityId(Integer id) {
        List<Faculty> faculties = facultyRepository.findByUniversityId(id);
        return faculties.stream()
                .map(customMapper::toFacultyResponse)
                .collect(Collectors.toSet());
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> deleteFaculty(Integer id) {
        if (!facultyRepository.existsById(id)) {
            throw new AppException(ErrorCode.FACULTY_NOT_EXISTED);
        }
        facultyRepository.deleteById(id);
        return ResponseEntity.ok("Faculty deleted successfully");
    }
}
