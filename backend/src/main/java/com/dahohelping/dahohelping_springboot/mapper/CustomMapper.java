package com.dahohelping.dahohelping_springboot.mapper;

import com.dahohelping.dahohelping_springboot.entity.*;
import com.dahohelping.dahohelping_springboot.service.dto.request.CommentCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.request.NotificationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomMapper {
    UniversityResponse toUniversityResponse(University university);
    FacultyResponse toFacultyResponse(Faculty faculty);
    MajorResponse toMajorResponse(Major major);
    SubjectResponse toSubjectResponse(Subject subject);
    CommentResponse toCommentResponse(Comment comment);
    Comment toComment(CommentCreationRequest commentCreationRequest);
    NotificationResponse toNotificationResponse(Notification notification);
    Notification toNotification(NotificationRequest notificationRequest);
    BadgeResponse toBadgeResponse(Badge badge);
}
