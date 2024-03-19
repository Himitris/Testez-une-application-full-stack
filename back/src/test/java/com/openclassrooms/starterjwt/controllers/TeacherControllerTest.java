package com.openclassrooms.starterjwt.controllers;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherController teacherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_TeacherFound() {
        // Given
        Long teacherId = 1L;
        Teacher teacher = new Teacher(teacherId, "Doe", "John", null, null);

        when(teacherService.findById(teacherId)).thenReturn(teacher);
        TeacherDto dto = new TeacherDto();
        dto.setId(teacherId);
        dto.setLastName(teacher.getLastName());
        dto.setFirstName(teacher.getFirstName());
        dto.setCreatedAt(teacher.getCreatedAt());
        dto.setUpdatedAt(teacher.getUpdatedAt());
        when(teacherMapper.toDto(teacher)).thenReturn(dto);

        // When
        ResponseEntity<?> response = teacherController.findById(teacherId.toString());

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testFindById_TeacherNotFound() {
        // Given
        Long teacherId = 1L;

        when(teacherService.findById(teacherId)).thenReturn(null);

        // When
        ResponseEntity<?> response = teacherController.findById(teacherId.toString());

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testFindAll() {
        // Given
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher(1L, "Doe", "John", null, null));
        teachers.add(new Teacher(2L, "Smith", "Jane", null, null));

        when(teacherService.findAll()).thenReturn(teachers);
        List<TeacherDto> teacherDtos = new ArrayList<>();
        for (Teacher teacher : teachers) {
            TeacherDto dto = new TeacherDto();
            dto.setId(teacher.getId());
            dto.setLastName(teacher.getLastName());
            dto.setFirstName(teacher.getFirstName());
            dto.setCreatedAt(teacher.getCreatedAt());
            dto.setUpdatedAt(teacher.getUpdatedAt());
            teacherDtos.add(dto);
        }

        when(teacherMapper.toDto(teachers)).thenReturn(teacherDtos);

        // When
        ResponseEntity<?> response = teacherController.findAll();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teacherDtos, response.getBody());
    }
}
