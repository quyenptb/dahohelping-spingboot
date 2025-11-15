package com.dahohelping.dahohelping_springboot.service;

import com.dahohelping.dahohelping_springboot.entity.Comment;
import com.dahohelping.dahohelping_springboot.mapper.CustomMapper;
import com.dahohelping.dahohelping_springboot.repository.CommentRepository;
import com.dahohelping.dahohelping_springboot.service.dto.request.CommentCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.CommentResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.NotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CustomMapper customMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;




    @Override
    public Optional<Comment> getCommentById(Integer id) {
        return Optional.empty();
    }

    /*
    LocalDateTime createdDate;
    String images;
    String text;
    Integer userId;
    String username;
    Integer cardId;
    Integer parentCommentId;
     */

    public Comment createComment(CommentCreationRequest request) {
        Optional<Comment> parentComment = commentRepository.findById(request.getParentCommentId());
        Comment comment = Comment.builder().text(request.getText())
                .createdDate(request.getCreatedDate())
                .images(request.getImages())
                .userId(request.getUserId())
                .username(request.getUsername())
                .cardId(request.getCardId())
                .parentComment(parentComment.get())
                .build();

        return comment;

    }
}
