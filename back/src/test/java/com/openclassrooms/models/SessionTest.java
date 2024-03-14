package com.openclassrooms.models;

import com.openclassrooms.starterjwt.models.Session;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
