package com.dahohelping.dahohelping_springboot.service;

import com.dahohelping.dahohelping_springboot.entity.Major;
import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.exception.AppException;
import com.dahohelping.dahohelping_springboot.exception.ErrorCode;
import com.dahohelping.dahohelping_springboot.mapper.CustomMapper;
import com.dahohelping.dahohelping_springboot.repository.MajorRepository;
import com.dahohelping.dahohelping_springboot.repository.UserRepository;
import com.dahohelping.dahohelping_springboot.service.dto.response.MajorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MajorService {

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private CustomMapper customMapper;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public MajorResponse getMyMajor() {
        User user = userRepository.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()
        );

        if (user != null && user.getMajor() != null) {
            return customMapper.toMajorResponse(user.getMajor());
        } else {
            throw new AppException(ErrorCode.MAJOR_NOT_EXISTED);
        }
    }

    public MajorResponse getMajorById(Integer id) {
        Major major = majorRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.MAJOR_NOT_EXISTED)
        );
        return customMapper.toMajorResponse(major);
    }

    public Set<MajorResponse> getMajorByNameContaining(String s) {
        List<Major> majors = majorRepository.findByNameContaining(s);
        return majors.stream()
                .map(customMapper::toMajorResponse)
                .collect(Collectors.toSet());
    }

    public MajorResponse getMajorByName(String name) {
        Major major = majorRepository.findByName(name).orElseThrow(() ->
                new AppException(ErrorCode.MAJOR_NOT_EXISTED)
        );
        return customMapper.toMajorResponse(major);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<MajorResponse> getAllMajors() {
        List<Major> majors = majorRepository.findAll();
        return majors.stream()
                .map(customMapper::toMajorResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public MajorResponse getMajorByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getMajor() != null) {
            return customMapper.toMajorResponse(user.getMajor());
        } else {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    }

    public Set<MajorResponse> getMajorsByFacultyId(Integer facultyId) {
        List<Major> majors = majorRepository.findByFacultyId(facultyId);
        return majors.stream()
                .map(customMapper::toMajorResponse)
                .collect(Collectors.toSet());
    }

    public Set<MajorResponse> getMajorsByUniversityId(Integer universityId) {
        List<Major> majors = majorRepository.findByUniversityId(universityId);
        return majors.stream()
                .map(customMapper::toMajorResponse)
                .collect(Collectors.toSet());
    }

    public Set<MajorResponse> getMajorsByUniversityCode(String code) {
        List<Major> majors = majorRepository.findByUniversityCode(code);
        return majors.stream()
                .map(customMapper::toMajorResponse)
                .collect(Collectors.toSet());
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> deleteMajor(Integer id) {
        if (!majorRepository.existsById(id)) {
            throw new AppException(ErrorCode.MAJOR_NOT_EXISTED);
        }
        majorRepository.deleteById(id);
        return ResponseEntity.ok("Major deleted successfully");
    }
}
