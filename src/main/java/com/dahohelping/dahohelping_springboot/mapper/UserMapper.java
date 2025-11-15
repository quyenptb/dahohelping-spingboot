package com.dahohelping.dahohelping_springboot.mapper;

import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserUpdateRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "Spring")
public interface UserMapper {
    @Mapping(source = "uniId", target = "university.id")
    @Mapping(source = "facId", target = "faculty.id")
    @Mapping(source = "majId", target = "major.id")
    @Mapping(source = "fullName", target = "fullName")
    User toUser(UserCreationRequest request);
    @Mapping(source = "university.id", target = "uniId")
    @Mapping(source = "faculty.id", target = "facId")
    @Mapping(source = "major.id", target = "majId")
    UserResponse toUserResponse(User user);

    User updateUser(UserUpdateRequest request);
}
