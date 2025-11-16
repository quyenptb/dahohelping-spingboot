package com.dahohelping.dahohelping_springboot.controller;

import com.dahohelping.dahohelping_springboot.service.PermissionService;
import com.dahohelping.dahohelping_springboot.service.RoleService;
import com.dahohelping.dahohelping_springboot.service.dto.request.PermissionRequest;
import com.dahohelping.dahohelping_springboot.service.dto.request.RoleRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.PermissionResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.RoleResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
        ApiResponse<RoleResponse> apiResponse = new ApiResponse<RoleResponse>();
        apiResponse.setResult(roleService.create(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll(){
        ApiResponse<List<RoleResponse>> apiResponse = new ApiResponse<List<RoleResponse>>();
        apiResponse.setResult(roleService.getAll());
        return apiResponse;
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable String role){
        roleService.delete(role);
        ApiResponse<Void> apiResponse = new ApiResponse<Void>();
        return apiResponse;
    }
}