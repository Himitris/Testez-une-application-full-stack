package com.openclassrooms.models;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;

import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testNameNotBlank() {
        // Given
        Session session = new Session();
        session.setName("");

        // When
        boolean isValid = validator.validate(session).isEmpty();

        // Then
        assertFalse(isValid);
    }

    @Test
    void testDescriptionNotNull() {
        // Given
        Session session = new Session();
        session.setDescription(null);

        // When
        boolean isValid = validator.validate(session).isEmpty();

        // Then
        assertFalse(isValid);
    }

    @Test
    void testDateNotNull() {
        // Given
        Session session = new Session();
        session.setDate(null);

        // When
        boolean isValid = validator.validate(session).isEmpty();

        // Then
        assertFalse(isValid);
    }

    @Test
    void testValidSession() {
        // Given
        Session session = new Session();
        session.setName("Test Session");
        session.setDescription("Test Description");
        session.setDate(new Date());

        // When
        boolean isValid = validator.validate(session).isEmpty();

        // Then
        assertTrue(isValid);
    }

    @Test
    void testEqualsWithIdenticalSessions() {
        Session session = new Session().setName("Test Session").setDate(new Date()).setDescription("Test Description");
        assertEquals(session, session);
    }

    @Test
    void testEqualsWithDifferentSessionSameValues() {
        Date date = new Date();
        Session session1 = new Session().setName("Test Session").setDate(date).setDescription("Test Description");
        Session session2 = new Session().setName("Test Session").setDate(date).setDescription("Test Description");
        assertEquals(session1, session2);
        assertEquals(session1.hashCode(), session2.hashCode());
    }

    // @Test
    // void testEqualsWithDifferentSessions() {
    //     Session session1 = new Session().setName("Test Session 1").setDate(new Date()).setDescription("Description 1");
    //     Session session2 = new Session().setName("Test Session 2").setDate(new Date()).setDescription("Description 2");
    //     assertNotEquals(session1, session2);
    // }

    @Test
    void testSessionConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = new Teacher().setFirstName("John").setLastName("Doe");
        List<User> users = Collections.emptyList(); // Assurez-vous d'importer java.util.Collections
        Session session = new Session(1L, "Session Name", new Date(), "Description", teacher, users, now, now);

        assertNotNull(session);
        assertEquals("Session Name", session.getName());
        assertEquals("Description", session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertTrue(session.getUsers().isEmpty());
        assertEquals(now, session.getCreatedAt());
        assertEquals(now, session.getUpdatedAt());
    }

    @Test
    void testSetTeacher() {
        Teacher teacher = new Teacher().setFirstName("Jane").setLastName("Doe");
        Session session = new Session();
        session.setTeacher(teacher);
        assertEquals(teacher, session.getTeacher());
    }

    @Test
    void testSetCreatedAt() {
        LocalDateTime createdAt = LocalDateTime.now();
        Session session = new Session();
        session.setCreatedAt(createdAt);
        assertEquals(createdAt, session.getCreatedAt());
    }

    @Test
    void testSetUpdatedAt() {
        LocalDateTime updatedAt = LocalDateTime.now();
        Session session = new Session();
        session.setUpdatedAt(updatedAt);
        assertEquals(updatedAt, session.getUpdatedAt());
    }

    @Test
    void testBuilder() {
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = new Teacher().setFirstName("John").setLastName("Doe");
        List<User> users = Collections.emptyList(); // Assurez-vous d'importer java.util.Collections
        Session session = Session.builder()
                .name("Builder Session")
                .date(new Date())
                .description("Built Description")
                .teacher(teacher)
                .users(users)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertNotNull(session);
        assertEquals("Builder Session", session.getName());
        assertEquals("Built Description", session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertTrue(session.getUsers().isEmpty());
        assertEquals(now, session.getCreatedAt());
        assertEquals(now, session.getUpdatedAt());
    }


}
