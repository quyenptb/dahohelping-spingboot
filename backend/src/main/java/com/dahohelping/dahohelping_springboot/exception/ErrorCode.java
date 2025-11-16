package com.dahohelping.dahohelping_springboot.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNIVERSITY_NOT_EXISTED(1006, "University not existed", HttpStatus.NOT_FOUND),
    FACULTY_NOT_EXISTED(1007, "Faculty not existed", HttpStatus.NOT_FOUND),
    MAJOR_NOT_EXISTED(1008, "Major not existed", HttpStatus.NOT_FOUND),
    SUBJECT_NOT_EXISTED(1009, "Subject not existed", HttpStatus.NOT_FOUND),
    NOTIFICATION_NOT_EXISTED(1010, "Notification not existed", HttpStatus.NOT_FOUND),
    CARD_NOT_EXISTED(1011, "Card not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1009, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1010, "You do not have permission", HttpStatus.FORBIDDEN),
    COMMENT_NOT_EXISTED(1011, "Comment is not existed", HttpStatus.NOT_FOUND),
    CARD_ALREADY_ANSWERED(1012, "Card is already answered", HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}