package com.openclassrooms.controllers;
import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_UserFound() {
        // Given
        Long userId = 1L;
        User user = new User(userId, "test@example.com", "Doe", "John", "password", false, null, null);

        when(userService.findById(userId)).thenReturn(user);
        UserDto dto = new UserDto();
        dto.setId(userId);
        dto.setEmail(user.getEmail());
        dto.setLastName(user.getLastName());
        dto.setFirstName(user.getFirstName());
        dto.setPassword(user.getPassword());
        when(userMapper.toDto(user)).thenReturn(dto);

        // When
        ResponseEntity<?> response = userController.findById(userId.toString());

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testFindById_UserNotFound() {
        // Given
        Long userId = 1L;

        when(userService.findById(userId)).thenReturn(null);

        // When
        ResponseEntity<?> response = userController.findById(userId.toString());

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testSave_UserDeleted() {
        // Given
        Long userId = 1L;
        User user = new User(userId, "test@example.com", "Doe", "John", "password", false, null, null);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userService.findById(userId)).thenReturn(user);

        // When
        ResponseEntity<?> response = userController.save(userId.toString());

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).delete(userId);
    }

}
