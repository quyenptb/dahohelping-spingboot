package com.dahohelping.dahohelping_springboot.service;

import com.dahohelping.dahohelping_springboot.entity.Subject;
import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.exception.AppException;
import com.dahohelping.dahohelping_springboot.exception.ErrorCode;
import com.dahohelping.dahohelping_springboot.mapper.CustomMapper;
import com.dahohelping.dahohelping_springboot.repository.SubjectRepository;
import com.dahohelping.dahohelping_springboot.repository.UserRepository;
import com.dahohelping.dahohelping_springboot.service.dto.response.SubjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CustomMapper customMapper;

    @Autowired
    private UserRepository userRepository;

    public SubjectResponse getSubjectById(Integer id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.SUBJECT_NOT_EXISTED)
        );
        return customMapper.toSubjectResponse(subject);
    }

    public List<SubjectResponse> getSubjectByNameContaining(String s) {
        List<Subject> subjects = subjectRepository.findByNameContaining(s);
        return subjects.stream()
                .map(customMapper::toSubjectResponse)
                .collect(Collectors.toList());
    }

    public SubjectResponse getSubjectByName(String name) {
        Subject subject = subjectRepository.findByName(name).orElseThrow(() ->
                new AppException(ErrorCode.SUBJECT_NOT_EXISTED)
        );
        return customMapper.toSubjectResponse(subject);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<SubjectResponse> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream()
                .map(customMapper::toSubjectResponse)
                .collect(Collectors.toList());
    }

    public Set<SubjectResponse> getSubjectsByMajorId(Integer majorId) {
        List<Subject> subjects = subjectRepository.findByMajorId(majorId);
        return subjects.stream()
                .map(customMapper::toSubjectResponse)
                .collect(Collectors.toSet());
    }

    public Set<SubjectResponse> getSubjectsByFacultyId(Integer facultyId) {
        List<Subject> subjects = subjectRepository.findByFacultyId(facultyId);
        return subjects.stream()
                .map(customMapper::toSubjectResponse)
                .collect(Collectors.toSet());
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> deleteSubject(Integer id) {
        if (!subjectRepository.existsById(id)) {
            throw new AppException(ErrorCode.SUBJECT_NOT_EXISTED);
        }
        subjectRepository.deleteById(id);
        return ResponseEntity.ok("Subject deleted successfully");
    }
}

