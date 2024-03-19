package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserById() {
        // Given
        Long userId = 1L;
        User user = new User(userId, "test@example.com", "Doe", "John", "password", false, LocalDateTime.now(), LocalDateTime.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        User result = userService.findById(userId);

        // Then
        assertEquals("test@example.com", result.getEmail());
        assertEquals("Doe", result.getLastName());
        assertEquals("John", result.getFirstName());
        assertEquals("password", result.getPassword());
        assertEquals(false, result.isAdmin());
    }

    @Test
    void testFindUserByIdNotFound() {
        // Given
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        User result = userService.findById(userId);

        // Then
        assertEquals(null, result);
    }

    @Test
    void testDeleteUser() {
        // Given
        Long userId = 1L;

        // When
        userService.delete(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }
}
