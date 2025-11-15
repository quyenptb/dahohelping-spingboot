package com.dahohelping.dahohelping_springboot.service;


import com.dahohelping.dahohelping_springboot.entity.Permission;
import com.dahohelping.dahohelping_springboot.mapper.PermissionMapper;
import com.dahohelping.dahohelping_springboot.repository.PermissionRepository;
import com.dahohelping.dahohelping_springboot.service.dto.request.PermissionRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.PermissionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PermissionService {
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request){ //create permission
        Permission permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll(){ //get all permission
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission){ //delete permission
        permissionRepository.deleteById(permission);
    }
}
