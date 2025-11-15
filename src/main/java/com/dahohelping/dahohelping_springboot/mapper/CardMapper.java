package com.dahohelping.dahohelping_springboot.mapper;

import com.dahohelping.dahohelping_springboot.entity.Card;
import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.service.dto.request.CardCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserUpdateRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.CardResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "Spring")
public interface CardMapper {
    @Mapping(target = "dahohelping.id", source = "dahohelpingId")
    @Mapping(target = "university.id", source = "universityId")
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "subject.id", source = "subjectId")
    @Mapping(target = "major.id", source = "majorId")
    @Mapping(target = "faculty.id", source = "facultyId")
    Card toCard(CardCreationRequest request);
    @Mapping(source = "university.id", target = "universityId")
    @Mapping(source = "faculty.id", target = "facultyId")
    @Mapping(source = "major.id", target = "majorId")
    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "images", target = "images")
    @Mapping(source = "dahohelping.id", target = "dahohelpingId")
    CardResponse toCardResponse(Card Card);
}
