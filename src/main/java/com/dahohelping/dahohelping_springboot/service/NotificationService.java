package com.dahohelping.dahohelping_springboot.service;

import com.dahohelping.dahohelping_springboot.entity.Notification;
import com.dahohelping.dahohelping_springboot.entity.Role;
import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.exception.AppException;
import com.dahohelping.dahohelping_springboot.exception.ErrorCode;
import com.dahohelping.dahohelping_springboot.mapper.CustomMapper;
import com.dahohelping.dahohelping_springboot.repository.NotificationRepository;
import com.dahohelping.dahohelping_springboot.service.dto.request.NotificationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.NotificationResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private CustomMapper customMapper;

    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(@RequestBody NotificationRequest request) {
        ApiResponse<NotificationResponse> apiResponse = new ApiResponse<NotificationResponse>();

        try {
            NotificationResponse savedNotification = customMapper.toNotificationResponse(notificationRepository.save(customMapper.toNotification(request)));
            apiResponse.setResult(savedNotification);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            apiResponse.setMessage("Error creating notification");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    public NotificationResponse getNotificationById(Integer id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.NOTIFICATION_NOT_EXISTED)
        );
        return customMapper.toNotificationResponse(notification);
    }

    public NotificationResponse getNotificationByTitle(String title) {
        Notification notification = notificationRepository.findByTitle(title).orElseThrow(() ->
                new AppException(ErrorCode.NOTIFICATION_NOT_EXISTED)
        );
        return customMapper.toNotificationResponse(notification);
    }

    public Set<NotificationResponse> getNotificationsByTitleContaining(String keyword) {
        List<Notification> notifications = notificationRepository.findByTitleContaining(keyword);
        return notifications.stream()
                .map(customMapper::toNotificationResponse)
                .collect(Collectors.toSet());
    }

    public Set<NotificationResponse> getNotificationsByOwnerId(Integer ownerId) {
        List<Notification> notifications = notificationRepository.findByOwnerId(ownerId);
        return notifications.stream()
                .map(customMapper::toNotificationResponse)
                .collect(Collectors.toSet());
    }

    public Set<NotificationResponse> getNotificationsByOwnerUsername(String ownerName) {
        List<Notification> notifications = notificationRepository.findByOwnerUsername(ownerName);
        return notifications.stream()
                .map(customMapper::toNotificationResponse)
                .collect(Collectors.toSet());
    }

    public Set<NotificationResponse> getNotificationsByReceiverId(Integer receiverId) {
        List<Notification> notifications = notificationRepository.findByReceiverId(receiverId);
        return notifications.stream()
                .map(customMapper::toNotificationResponse)
                .collect(Collectors.toSet());
    }

    public Set<NotificationResponse> getNotificationsByReceiverUsername(String receiverName) {
        List<Notification> notifications = notificationRepository.findByReceiverUsername(receiverName);
        return notifications.stream()
                .map(customMapper::toNotificationResponse)
                .collect(Collectors.toSet());
    }

    public Set<NotificationResponse> getNotificationsByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<Notification> notifications = notificationRepository.findByCreatedDateBetween(startDate, endDate);
        return notifications.stream()
                .map(customMapper::toNotificationResponse)
                .collect(Collectors.toSet());
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> deleteNotificationById(Integer id) {
        if (!notificationRepository.existsById(id)) {
            throw new AppException(ErrorCode.NOTIFICATION_NOT_EXISTED);
        }
        notificationRepository.deleteById(id);
        return ResponseEntity.ok("Notification deleted successfully");
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> deleteNotificationByTitle(String title) {
        Optional<Notification> notification = notificationRepository.findByTitle(title);
        if (!notification.isPresent()) {
            throw new AppException(ErrorCode.NOTIFICATION_NOT_EXISTED);
        }
        notificationRepository.deleteByTitle(title);
        return ResponseEntity.ok("Notification deleted successfully");
    }
}
