package com.dahohelping.dahohelping_springboot;


import com.dahohelping.dahohelping_springboot.entity.*;
import com.dahohelping.dahohelping_springboot.exception.AppException;
import com.dahohelping.dahohelping_springboot.mapper.UserMapper;
import com.dahohelping.dahohelping_springboot.repository.UserRepository;
import com.dahohelping.dahohelping_springboot.service.UserService;
import com.dahohelping.dahohelping_springboot.service.dto.request.UserCreationRequest;
import com.dahohelping.dahohelping_springboot.service.dto.response.ApiResponse;
import com.dahohelping.dahohelping_springboot.service.dto.response.UserResponse;
import jakarta.persistence.Column;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Mock
    private static PasswordEncoder passwordEncoder;

    private User happyUser;

    private UserResponse userReponse;



    @BeforeEach
    void setUp() {

        // University
        University uni = University.builder()
                .id(1)
                .name("Đại học A")
                .code("UA")
                .icon("icon.png")
                .build();

        Faculty faculty = Faculty.builder()
                .id(2)
                .name("Khoa B")
                .university(uni)
                .build();

        Major major = Major.builder()
                .id(3)
                .name("Ngành C")
                .faculty(faculty)
                .build();

        Role userRole = Role.builder().name("User").description("For user").build();


        happyUser = User.builder()
                .username("happyUser")
                .password("1234567")
                .email("happyUser@gmail.com")
                .university(uni)
                .faculty(faculty)
                .major(major)
                .roles(Set.of(userRole))
                .build();

        userReponse = new UserResponse(happyUser.getUsername(), happyUser.getEmail());


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

    @Test
    @DisplayName("createUsser_whenUsernameExisted_shouldThrowException")
    void createUsser_whenUsernameExisted_shouldThrowException() {
        // Arrange
        UserCreationRequest userCreationRequest = UserCreationRequest.builder()
                .username("existedUser").password("123456789").fullName("Existed User").uniId("1").facId("2").majId("3")
                .build();
        // Act
        when(userRepository.existsByUsername(userCreationRequest.getUsername())).thenReturn(true);

        Exception thrown = assertThrows(AppException.class, () -> userService.createUser(userCreationRequest));
        // Assert

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("createUser_whenUserIsValid_shouldCreateUserSucessfully")
    void createUser_whenUserIsValid_shouldCreateUserSucessfully() {
        // Arrange
        UserCreationRequest userCreationRequest = UserCreationRequest.builder()
                .username("happyUser").password("1234567").email("happyUser@gmail.com").fullName("happyUser").uniId("1").facId("2").majId("3")
                .build();

        when(userRepository.existsByUsername(userCreationRequest.getUsername())).thenReturn(false);
        when(userMapper.toUser(userCreationRequest)).thenReturn(happyUser);
        when(passwordEncoder.encode(userCreationRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userMapper.toUserResponse(any(User.class))).thenReturn(
                userReponse
        );

        // Act

        UserResponse response = userService.createUser(userCreationRequest);

        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        // Assert
        assertEquals("happyUser", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(1, savedUser.getRoles().size());
        assertTrue(savedUser.getRoles().stream().anyMatch(r -> r.getName().equals("USER")));

        verify(userRepository, times(1)).save(any(User.class));

    }


}
