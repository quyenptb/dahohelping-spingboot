package com.dahohelping.dahohelping_springboot.service;

import com.dahohelping.dahohelping_springboot.entity.Role;
import com.dahohelping.dahohelping_springboot.entity.University;
import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.exception.AppException;
import com.dahohelping.dahohelping_springboot.exception.ErrorCode;
import com.dahohelping.dahohelping_springboot.mapper.CustomMapper;
import com.dahohelping.dahohelping_springboot.mapper.UserMapper;
import com.dahohelping.dahohelping_springboot.repository.UniversityRepository;
import com.dahohelping.dahohelping_springboot.repository.UserRepository;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserUpdateRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.UniversityResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UniversityService {
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private CustomMapper customMapper;
    @Autowired
    private UserRepository userRepository;

    public UniversityResponse getMyUniversity() {
        User user = userRepository.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()
        );

        if (user.getUniversity() != null) { //check name of this user
            return customMapper.toUniversityResponse(user.getUniversity());
        }
        else throw new RuntimeException("University or User is not existed");
    }

    public UniversityResponse getUniversityById(Integer id) {
        return customMapper.toUniversityResponse(
                universityRepository.findUniversityById(id)
        );
    }

    public UniversityResponse getUniversityByCode(String code) {
        return customMapper.toUniversityResponse(
                universityRepository.findUniversityByCode(code)
        );
    }

    public List<UniversityResponse> getUniversityByNameContaining(String s) {
        List<University> list = universityRepository.findByNameContaining(s);
        List<UniversityResponse> _list = new ArrayList<>();
        for (University i : list ) {
            _list.add(customMapper.toUniversityResponse(i));
        }
        return _list;
    }

    public UniversityResponse getUniversityByName(String name) {
        return customMapper.toUniversityResponse(
                universityRepository.findByUniversityName(name)
        );
    }

    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<UniversityResponse> getAllUniversity() {
        List<University> list = universityRepository.findAll();
        List<UniversityResponse> _list = new ArrayList<>(List.of());
        for (University i : list ) {
            _list.add(customMapper.toUniversityResponse(i));
        }
        return _list;
    }

    public UniversityResponse getUniversityByUsername(String username) {
        User user = userRepository.findByUsername(username);
         if ( user  != null) {
            return customMapper.toUniversityResponse(user.getUniversity());
        }
        else throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> deleteUniversity(Integer id){
        universityRepository.deleteById(id);
            return ResponseEntity.ok("University deleted successfully");
        }
    }


