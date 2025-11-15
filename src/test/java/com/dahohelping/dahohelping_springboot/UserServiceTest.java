package com.dahohelping.dahohelping_springboot;


import com.dahohelping.dahohelping_springboot.entity.User;
import com.dahohelping.dahohelping_springboot.exception.AppException;
import com.dahohelping.dahohelping_springboot.mapper.UserMapper;
import com.dahohelping.dahohelping_springboot.repository.UserRepository;
import com.dahohelping.dahohelping_springboot.service.UserService;
import com.dahohelping.dahohelping_springboot.service.dto.response.UserResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;
    
    private static PasswordEncoder passwordEncoder;

    private User happyUser;


    @BeforeEach
    void setUp() {
        
        //User happy path
        happyUser = User.builder().username("happyUser").email("happyUser@gmail.com").password("happyUser").build();
        
    }
    
    @BeforeAll
    static void setAll() {
       
        
    }

    @Test
    @DisplayName("getRanking_whenLimitSmallerThanZero_shouldReturnException")
    void getRanking_whenLimitSmallerThanZero_shouldReturnException() {
        // Arrange
        
        // Act

        // Assert
        Exception thrown = assertThrows(IllegalArgumentException.class, () -> userService.getRanking(-5));
    }

    @Test
    @DisplayName("getRanking_whenLimitGreaterThanTotalUser_shouldReturnAvailableUser")
    void getRanking_whenLimitGreaterThanTotalUser_shouldReturnAvailableUser() {
        // Arrange
        int invalidLimit = 100;
        User user1 = User.builder().username("user1").password("user1").build();
        User user2 = User.builder().username("user2").password("user2").build();
        // Act
        when(userRepository.getRanking(PageRequest.of(0, invalidLimit))).thenReturn(List.of(user1, user2));
        // Assert
        List<UserResponse> ranking = userService.getRanking(invalidLimit);

        assertEquals(2, ranking.size());

    }

    @Test
    @DisplayName("getRanking_whenLimitIsValid_shouldReturnRanking")
    void getRanking_whenLimitIsValid_shouldReturnRanking() {
        // Arrange
        int validLimit = 2;
        User user1 = User.builder().username("user1").password("user1").build();
        User user2 = User.builder().username("user2").password("user2").build();

        List<User> mockUsers = List.of(user1, user2);
        // Act
        when(userRepository.getRanking(PageRequest.of(0, validLimit))).thenReturn(mockUsers);

        when(userMapper.toUserResponse(any(User.class)))
                .thenAnswer(invocation -> {
                    User u = invocation.getArgument(0);
                    return new UserResponse(u.getUsername(), u.getEmail());
                });



        List<UserResponse> ranking = userService.getRanking(validLimit);
        // Assert
        assertEquals(validLimit, ranking.size());

        verify(userMapper, times(mockUsers.size())).toUserResponse(any(User.class));

    }


/*
 một user khi đăng nhập vào thì hiển nhiên có context rồi
                chứ edge case:
- ai đó cố tình chưa login mà lại muốn get info -> không có context
- user đã login như db lại xóa user đó -> authentication null

 */
    //User chưa login
    @Test
    @DisplayName("getMyInfo_whenUserIsNotAuthenticated_shouldReturnException")
    void getMyInfo_whenUserIsNotAuthenticated_shouldReturnException() {
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(context);

        Exception thrown = assertThrows(AppException.class, () -> userService.getMyInfo());
    }

    //User đã login nhưng bị xóa trong db
    @Test
    @DisplayName("getMyInfo_whenUserIsNullFromDB_shouldReturnException")
    void getMyInfo_whenUserIsNullFromDB_shouldReturnException() {
        // Arrange
        Authentication auth = mock(Authentication.class);

        when(auth.getName()).thenReturn("deletedUser");

        SecurityContext context = mock(SecurityContext.class);

        when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);

        when(userRepository.findByUsername("deletedUser")).thenReturn(null);

        Exception thrown = assertThrows(AppException.class, () -> userService.getMyInfo());

    }

    @Test
    @DisplayName("getMyInfo_whenUserIsAuthenticatedAndValid_shouldReturnUserInfo")
    void getMyInfo_whenUserIsAuthenticatedAndValid_shouldReturnUserInfo() {
        // Arrange
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("happyUser");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);

        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);

        when(userRepository.findByUsername("happyUser")).thenReturn(happyUser);
        when(userMapper.toUserResponse(happyUser))
                .thenReturn(new UserResponse(happyUser.getUsername(), happyUser.getEmail()));

        // Act
        UserResponse user = userService.getMyInfo();

        // Assert
        assertEquals("happyUser", user.getUsername());

        verify(userRepository, times(1)).findByUsername("happyUser");
        verify(userMapper, times(1)).toUserResponse(happyUser);

    }




}
