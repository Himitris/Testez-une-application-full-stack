package com.openclassrooms.models;

import org.junit.jupiter.api.Test;

import com.openclassrooms.starterjwt.models.User;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

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
    void testConstructor() {
        // Given
        Long id = 1L;
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String password = "password123";
        boolean admin = false;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // When
        User user = new User(email, lastName, firstName, password, admin);
        User user2 = new User(id, email, lastName, firstName, password, admin, createdAt, updatedAt);

        // Then
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(password, user.getPassword());
        assertEquals(admin, user.isAdmin());

        
        assertNotNull(user2);
        assertEquals(id, user2.getId());
        assertEquals(email, user2.getEmail());
        assertEquals(firstName, user2.getFirstName());
        assertEquals(lastName, user2.getLastName());
        assertEquals(password, user2.getPassword());
        assertEquals(admin, user2.isAdmin());
        assertEquals(createdAt, user2.getCreatedAt());
        assertEquals(updatedAt, user2.getUpdatedAt());
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
    void testEqualsAndHashCode() {
        // Given
        Long id = 1L;
        User user1 = new User(id, "test@example.com", "Doe", "John", "password123", false, null, null);
        User user2 = new User(id, "test@example.com", "Doe", "Jane", "password123", false, null, null);

        // Then
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }


}
