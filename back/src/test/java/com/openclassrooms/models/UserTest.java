package com.openclassrooms.models;

import org.junit.jupiter.api.Test;

import com.openclassrooms.starterjwt.models.User;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testValidUser() {
        // Given
        User user = User.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(false)
                .build();

        // When
        boolean isValid = validator.validate(user).isEmpty();

        // Then
        assertTrue(isValid);
    }
    @Test
    void testEmailValidFormat() {
        // Given
        User user = User.builder()
                .email("notavalidemail")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(false)
                .build();

        // When
        boolean isValid = validator.validate(user).isEmpty();

        // Then
        assertFalse(isValid);
    }
}
