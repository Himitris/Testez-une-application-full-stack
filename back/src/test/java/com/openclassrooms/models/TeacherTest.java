package com.openclassrooms.models;

import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

class TeacherTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testEqualsWithSameObject() {
        Teacher teacher = new Teacher().setFirstName("John").setLastName("Doe");
        assertEquals(teacher, teacher);
    }

    @Test
    void testEqualsWithDifferentObjectSameValues() {
        Teacher teacher1 = new Teacher().setFirstName("John").setLastName("Doe");
        Teacher teacher2 = new Teacher().setFirstName("John").setLastName("Doe");
        assertEquals(teacher1, teacher2);
    }

    @Test
    void testToString() {
        Teacher teacher = new Teacher().setFirstName("John").setLastName("Doe");
        String expectedString = "Teacher(id=null, lastName=Doe, firstName=John, createdAt=null, updatedAt=null)";
        assertEquals(expectedString, teacher.toString());
    }

    @Test
    void testHashCodeConsistency() {
        Teacher teacher = new Teacher().setFirstName("John").setLastName("Doe");
        int initialHashCode = teacher.hashCode();
        int repeatedHashCode = teacher.hashCode();
        assertEquals(initialHashCode, repeatedHashCode);
    }

    @Test
    void testHashCodeEquality() {
        Teacher teacher1 = new Teacher().setFirstName("John").setLastName("Doe");
        Teacher teacher2 = new Teacher().setFirstName("John").setLastName("Doe");
        assertEquals(teacher1.hashCode(), teacher2.hashCode());
    }

    @Test
    void testSetId() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        assertEquals(1L, teacher.getId());
    }

    @Test
    void testSetCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = new Teacher();
        teacher.setCreatedAt(now);
        assertEquals(now, teacher.getCreatedAt());
    }

    @Test
    void testSetUpdatedAt() {
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = new Teacher();
        teacher.setUpdatedAt(now);
        assertEquals(now, teacher.getUpdatedAt());
    }

    @Test
    void testBuilder() {
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = Teacher.builder()
                .firstName("John")
                .lastName("Doe")
                .createdAt(now)
                .updatedAt(now)
                .build();
        
        assertNotNull(teacher);
        assertEquals("John", teacher.getFirstName());
        assertEquals("Doe", teacher.getLastName());
        assertEquals(now, teacher.getCreatedAt());
        assertEquals(now, teacher.getUpdatedAt());
    }


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
