package com.dahohelping.dahohelping_springboot.controller;

import com.dahohelping.dahohelping_springboot.service.NotificationService;
import com.dahohelping.dahohelping_springboot.service.dto.request.NotificationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.NotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(@RequestBody NotificationRequest request) {
        return notificationService.createNotification(request);
    }

    @GetMapping("/{id}")
    public NotificationResponse getNotificationById(@PathVariable Integer id) {
        return notificationService.getNotificationById(id);
    }

    @GetMapping("/title/{title}")
    public NotificationResponse getNotificationByTitle(@PathVariable String title) {
        return notificationService.getNotificationByTitle(title);
    }

    @GetMapping("/titleContaining/{keyword}")
    public Set<NotificationResponse> getNotificationsByTitleContaining(@PathVariable String keyword) {
        return notificationService.getNotificationsByTitleContaining(keyword);
    }

    @GetMapping("/ownerId/{ownerId}")
    public Set<NotificationResponse> getNotificationsByOwnerId(@PathVariable Integer ownerId) {
        return notificationService.getNotificationsByOwnerId(ownerId);
    }

    @GetMapping("/ownerUsername/{ownerName}")
    public Set<NotificationResponse> getNotificationsByOwnerUsername(@PathVariable String ownerName) {
        return notificationService.getNotificationsByOwnerUsername(ownerName);
    }

    @GetMapping("/receiverId/{receiverId}")
    public Set<NotificationResponse> getNotificationsByReceiverId(@PathVariable Integer receiverId) {
        return notificationService.getNotificationsByReceiverId(receiverId);
    }

    @GetMapping("/receiverUsername/{receiverName}")
    public Set<NotificationResponse> getNotificationsByReceiverUsername(@PathVariable String receiverName) {
        return notificationService.getNotificationsByReceiverUsername(receiverName);
    }

    @GetMapping("/createdDateBetween")
    public Set<NotificationResponse> getNotificationsByCreatedDateBetween(@RequestParam("startDate") LocalDateTime startDate,
                                                                          @RequestParam("endDate") LocalDateTime endDate) {
        return notificationService.getNotificationsByCreatedDateBetween(startDate, endDate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotificationById(@PathVariable Integer id) {
        return notificationService.deleteNotificationById(id);
    }

    @DeleteMapping("/title/{title}")
    public ResponseEntity<?> deleteNotificationByTitle(@PathVariable String title) {
        return notificationService.deleteNotificationByTitle(title);
    }
}
