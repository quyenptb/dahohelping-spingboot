package com.dahohelping.dahohelping_springboot.controller;

import com.dahohelping.dahohelping_springboot.service.PermissionService;
import com.dahohelping.dahohelping_springboot.service.dto.request.PermissionRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.PermissionResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request){
        ApiResponse<PermissionResponse> apiResponse = new ApiResponse<PermissionResponse>();
        apiResponse.setResult(permissionService.create(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll(){
        ApiResponse<List<PermissionResponse>> apiResponse = new ApiResponse<List<PermissionResponse>>();
        apiResponse.setResult(permissionService.getAll());
        return apiResponse;
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable String permission){
        permissionService.delete(permission);
        ApiResponse<Void> apiResponse = new ApiResponse<Void>();
        return apiResponse;
    }
}