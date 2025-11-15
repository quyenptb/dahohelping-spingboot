package com.dahohelping.dahohelping_springboot.service;

import com.dahohelping.dahohelping_springboot.mapper.RoleMapper;
import com.dahohelping.dahohelping_springboot.repository.PermissionRepository;
import com.dahohelping.dahohelping_springboot.repository.RoleRepository;
import com.dahohelping.dahohelping_springboot.service.dto.request.RoleRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.RoleResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) { //create Role
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() { //get all Role
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public void delete(String role) { //delete Role
        roleRepository.deleteById(role);
    }
}
