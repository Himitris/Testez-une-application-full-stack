package com.openclassrooms.models;

import org.junit.jupiter.api.Test;

import com.openclassrooms.starterjwt.models.User;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

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
    void testToString() {
        User user = User.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(false)
                .build();

        String expected = "User(id=null, email=test@example.com, lastName=Doe, firstName=John, password=password123, admin=false, createdAt=null, updatedAt=null)";
        assertEquals(expected, user.toString());
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
    
    @Test
    void testEqualsWithIdenticalUsers() {
        User user1 = new User("test@example.com", "John", "Doe", "password123", false);
        User user2 = user1;
        assertEquals(user1, user2);
    }

    @Test
    void testEqualsWithEquivalentUsers() {
        User user1 = new User("test@example.com", "John", "Doe", "password123", false);
        User user2 = new User("test@example.com", "John", "Doe", "password123", false);
        assertEquals(user1, user2);
    }

    @Test
    void testEqualsWithDifferentUsers() {
        User user1 = new User("test@example.com", "John", "Doe", "password123", false);
        User user2 = new User("different@example.com", "Jane", "Doe", "password123", false);
        assertEquals(user1, user2);
    }

    

    @Test
    void testEqualsWithDifferentIds() {
        User user1 = new User(1L, "test@example.com", "John", "Doe", "password123", false, null, null);
        User user2 = new User(2L, "test@example.com", "John", "Doe", "password123", false, null, null);

        assertNotEquals(user1, user2);
    }

    @Test
    void testEqualsWithDifferentTimestamps() {
        LocalDateTime time1 = LocalDateTime.of(2020, 1, 1, 12, 0);
        LocalDateTime time2 = LocalDateTime.now();
        User user1 = new User(1L, "test@example.com", "John", "Doe", "password123", false, time1, time1);
        User user2 = new User(1L, "test@example.com", "John", "Doe", "password123", false, time2, time2);

        assertEquals(user1, user2);
    }

    @Test
    void testHashCodeWithEquivalentUsers() {
        User user1 = new User("test@example.com", "John", "Doe", "password123", false);
        User user2 = new User("test@example.com", "John", "Doe", "password123", false);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testUserConstructorWithIdAndTimestamps() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(1L, "test@example.com", "Doe", "John", "password123", false, now, now);

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("password123", user.getPassword());
        assertFalse(user.isAdmin());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void testUserConstructorWithoutIdAndTimestamps() {
        User user = new User("test@example.com", "Doe", "John", "password123", false);

        assertNotNull(user);
        assertNull(user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("password123", user.getPassword());
        assertFalse(user.isAdmin());
        assertNull(user.getCreatedAt());
        assertNull(user.getUpdatedAt());
    }

}
