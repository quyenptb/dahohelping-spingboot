package com.dahohelping.dahohelping_springboot.service;

import com.dahohelping.dahohelping_springboot.entity.Card;
import com.dahohelping.dahohelping_springboot.entity.Comment;
import com.dahohelping.dahohelping_springboot.entity.Role;
import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.exception.AppException;
import com.dahohelping.dahohelping_springboot.exception.ErrorCode;
import com.dahohelping.dahohelping_springboot.mapper.UserMapper;
import com.dahohelping.dahohelping_springboot.repository.CardRepository;
import com.dahohelping.dahohelping_springboot.repository.CommentRepository;
import com.dahohelping.dahohelping_springboot.repository.UserRepository;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserUpdateRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CardRepository cardRepository;


    public List<UserResponse> getRanking(int limit) {
        PageRequest top = PageRequest.of(0, limit);
        List<User> topUsers = userRepository.getRanking(top);
        return topUsers.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }


    public UserResponse getMyInfo() {
        User authenticatedUser = getAuthenticatedUser();
        return userMapper.toUserResponse(authenticatedUser);
    }

    @Transactional
    public UserResponse updateMyProfile(UserUpdateRequest request) {
        User authenticatedUser = getAuthenticatedUser();

        if (request.getAvatar() != null) authenticatedUser.setAvatar(request.getAvatar());
        if (request.getFullName() != null) authenticatedUser.setFullName(request.getFullName());
        if (request.getEmail() != null) authenticatedUser.setEmail(request.getEmail());
        if (request.getHometown() != null) authenticatedUser.setHometown(request.getHometown());
        if (request.getHobby() != null) authenticatedUser.setHobby(request.getHobby());
        if (request.getBio() != null) authenticatedUser.setBio(request.getBio());

        User savedUser = userRepository.save(authenticatedUser);
        return userMapper.toUserResponse(savedUser);
    }

    @Transactional
    public void chooseTheBestQuestion(Integer commentId) {
        User authenticatedUser = getAuthenticatedUser();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_EXISTED));

        Card card = cardRepository.findById(comment.getCardId())
                .orElseThrow(() -> new AppException(ErrorCode.CARD_NOT_EXISTED));

        if (!card.getUser().getId().equals(authenticatedUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        if (card.getIsAnswered() != null && card.getIsAnswered()) {
            throw new AppException(ErrorCode.CARD_ALREADY_ANSWERED);
        }


        User commenter = userRepository._findById(comment.getUserId());
        if (commenter == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        Integer scoreAward = card.getAwardScore();

        if (scoreAward != null && scoreAward > 0) {
            commenter.setScore(commenter.getScore() + scoreAward);
            userRepository.save(commenter);
        }

        card.setIsAnswered(true);
        cardRepository.save(card);
    }

    public Integer getScoreById(Integer userId) {
        User user = userRepository._findById(userId);
        if (user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        return user.getScore();
    }


    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }


    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        return userMapper.toUserResponse(user);
    }

    public UserResponse getUserById(Integer userId) {
        User user = userRepository._findById(userId);
        if (user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getUsersByUsernameContaining(String keyword) {
        return userRepository.findByUsernameContaining(keyword)
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse createUser(UserCreationRequest request) {
        //check if username is existed
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        Role role = new Role();
        role.setName(com.dahohelping.dahohelping_springboot.roles.Role.USER.name());
        user.setRoles(Set.of(role));

        User savedUser = userRepository.save(user);
        UserResponse savedResponse = userMapper.toUserResponse(savedUser);


        return savedResponse;
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> deleteUser(Integer userId) {
        User user = userRepository._findById(userId);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        userRepository.delete(user);
        return ResponseEntity.ok("User deleted successfully");
    }

    public ResponseEntity<?> deleteMe(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        userRepository.delete(user);
        return ResponseEntity.ok("User deleted successfully");
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        String username = auth.getName();
        User user = userRepository.findByUsername(username);
        if (user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        return user;
    }
}