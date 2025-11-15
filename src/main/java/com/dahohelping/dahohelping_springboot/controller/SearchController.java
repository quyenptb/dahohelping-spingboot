package com.dahohelping.dahohelping_springboot.controller;

import com.dahohelping.dahohelping_springboot.service.*;
import com.dahohelping.dahohelping_springboot.service.dto.response.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private UniversityService universityService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public Set<?> performSearch(@RequestParam String slug, @RequestParam String type) {
        if (slug == null || slug.isEmpty() || type == null || type.isEmpty()) {
            Set<?> _list = new HashSet<>();
            //throw new IllegalArgumentException("Slug và Type không được để trống");
        }
        Set<?> _list = null;
        switch (type) {
            case "Người dùng":
                _list = userService.getUsersByUsernameContaining(slug);
                break;
            case "Câu hỏi":
                _list = cardService.getCardsByTitleContaining(slug);
                break;
            case "Trường Đại học":
                _list = universityService.getUniversityByNameContaining(slug);
                break;
            case "Khoa":
                _list = facultyService.getFacultyByNameContaining(slug);
                break;
            case "Ngành":
                _list = majorService.getMajorByNameContaining(slug);
                break;
            case "Môn học":
                _list = subjectService.getSubjectByNameContaining(slug);
                break;
            default:
                throw new IllegalArgumentException("Loại tìm kiếm không hợp lệ");
        }

        //ApiResponse<Set<?>> apiResponse = new ApiResponse<>();
        //apiResponse.setResult(_list);
        return _list;
    }
}

