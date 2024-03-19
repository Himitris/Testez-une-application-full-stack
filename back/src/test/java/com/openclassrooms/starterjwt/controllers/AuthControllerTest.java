package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateUser() {
        // Given
        String email = "test@example.com";
        String password = "password";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, email, "John", "Doe", true, password);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        String jwt = "mockedJWT";
        when(jwtUtils.generateJwtToken(authentication)).thenReturn(jwt);

        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAdmin(true);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // When
        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertEquals(jwt, jwtResponse.getToken());
        assertEquals(user.getId(), jwtResponse.getId());
        assertEquals(user.getEmail(), jwtResponse.getUsername());
        assertEquals(user.getFirstName(), jwtResponse.getFirstName());
        assertEquals(user.getLastName(), jwtResponse.getLastName());
    }

    @Test
    void testRegisterUser() {
        // Given
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String password = "password";
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setEmail(email);
        signUpRequest.setFirstName(firstName);
        signUpRequest.setLastName(lastName);
        signUpRequest.setPassword(password);

        when(userRepository.existsByEmail(email)).thenReturn(false);

        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn(encodedPassword);
        
        User savedUser = new User(email, lastName, firstName, passwordEncoder.encode(password), false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        ResponseEntity<?> response = authController.registerUser(signUpRequest);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals("User registered successfully!", messageResponse.getMessage());

        verify(userRepository, times(1)).existsByEmail(email);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserWithEmailAlreadyTaken() {
        // Given
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String password = "password";
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setEmail(email);
        signUpRequest.setFirstName(firstName);
        signUpRequest.setLastName(lastName);
        signUpRequest.setPassword(password);

        when(userRepository.existsByEmail(email)).thenReturn(true);

        // When
        ResponseEntity<?> response = authController.registerUser(signUpRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals("Error: Email is already taken!", messageResponse.getMessage());

        verify(userRepository, times(1)).existsByEmail(email);
        verify(userRepository, never()).save(any(User.class));
    }
}
