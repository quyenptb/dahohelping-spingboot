package com.dahohelping.dahohelping_springboot.controller;

import com.dahohelping.dahohelping_springboot.entity.Comment;
import com.dahohelping.dahohelping_springboot.service.CommentService;
import com.dahohelping.dahohelping_springboot.service.dto.request.CommentCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.CommentResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.NotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public Optional<Comment> getCommentById(@PathVariable Integer id) {
        return commentService.getCommentById(id);
    }

    @GetMapping("/card/{id}")
    public List<Comment> getCommentByCardId(@PathVariable Integer id) {
        return commentService.getCommentByCardId(id);
    }


    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentCreationRequest request) {
        Comment createdComment = commentService.createComment(request);
        if (createdComment != null) {
            return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create comment");
        }
    }

}
