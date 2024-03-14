package com.openclassrooms.models;

import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TeacherTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testLastNameNotBlank() {
        // Given
        Teacher teacher = new Teacher();
        teacher.setLastName("");

        // When
        boolean isValid = validator.validate(teacher).isEmpty();

        // Then
        assertFalse(isValid);
    }

    @Test
    void testFirstNameNotBlank() {
        // Given
        Teacher teacher = new Teacher();
        teacher.setFirstName("");

        // When
        boolean isValid = validator.validate(teacher).isEmpty();

        // Then
        assertFalse(isValid);
    }

    @Test
    void testValidTeacher() {
        // Given
        Teacher teacher = new Teacher();
        teacher.setLastName("Doe");
        teacher.setFirstName("John");

        // When
        boolean isValid = validator.validate(teacher).isEmpty();

        // Then
        assertTrue(isValid);
    }
}
