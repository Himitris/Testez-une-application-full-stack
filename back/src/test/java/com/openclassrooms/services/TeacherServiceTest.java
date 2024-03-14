package com.openclassrooms.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllTeachers() {
        // Given
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now()));
        teachers.add(new Teacher(2L, "Smith", "Jane", LocalDateTime.now(), LocalDateTime.now()));

        when(teacherRepository.findAll()).thenReturn(teachers);

        // When
        List<Teacher> result = teacherService.findAll();

        // Then
        assertEquals(2, result.size());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Smith", result.get(1).getLastName());
        assertEquals("Jane", result.get(1).getFirstName());
    }

    @Test
    void testFindTeacherById() {
        // Given
        Long teacherId = 1L;
        Teacher teacher = new Teacher(teacherId, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        // When
        Teacher result = teacherService.findById(teacherId);

        // Then
        assertEquals("Doe", result.getLastName());
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testFindTeacherByIdNotFound() {
        // Given
        Long teacherId = 1L;

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        // When
        Teacher result = teacherService.findById(teacherId);

        // Then
        assertEquals(null, result);
    }
}
