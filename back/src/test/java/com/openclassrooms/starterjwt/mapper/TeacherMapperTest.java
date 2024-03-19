package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;

class TeacherMapperTest {

    private TeacherMapper teacherMapper = new TeacherMapperImpl();

    @Test
    void testToEntity() {
        // Créer un objet TeacherDto à mapper vers Teacher
        TeacherDto teacherDto = new TeacherDto(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());

        // Mapper l'objet TeacherDto vers Teacher
        Teacher teacher = teacherMapper.toEntity(teacherDto);

        // Vérifier si les champs sont correctement mappés
        assertEquals(teacherDto.getId(), teacher.getId());
        assertEquals(teacherDto.getLastName(), teacher.getLastName());
        assertEquals(teacherDto.getFirstName(), teacher.getFirstName());
        assertEquals(teacherDto.getCreatedAt(), teacher.getCreatedAt());
        assertEquals(teacherDto.getUpdatedAt(), teacher.getUpdatedAt());
    }

    @Test
    void testToDto() {
        // Créer un objet Teacher à mapper vers TeacherDto
        Teacher teacher = new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());

        // Mapper l'objet Teacher vers TeacherDto
        TeacherDto teacherDto = teacherMapper.toDto(teacher);

        // Vérifier si les champs sont correctement mappés
        assertEquals(teacher.getId(), teacherDto.getId());
        assertEquals(teacher.getLastName(), teacherDto.getLastName());
        assertEquals(teacher.getFirstName(), teacherDto.getFirstName());
        assertEquals(teacher.getCreatedAt(), teacherDto.getCreatedAt());
        assertEquals(teacher.getUpdatedAt(), teacherDto.getUpdatedAt());
    }

    @Test
    void testToEntityList() {
        // Créer une liste d'objets TeacherDto à mapper vers une liste d'objets Teacher
        List<TeacherDto> teacherDtos = Arrays.asList(
            new TeacherDto(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now()),
            new TeacherDto(2L, "Smith", "Jane", LocalDateTime.now(), LocalDateTime.now())
        );

        // Mapper la liste d'objets TeacherDto vers une liste d'objets Teacher
        List<Teacher> teachers = teacherMapper.toEntity(teacherDtos);

        // Vérifier si les champs sont correctement mappés pour chaque objet
        for (int i = 0; i < teacherDtos.size(); i++) {
            assertEquals(teacherDtos.get(i).getId(), teachers.get(i).getId());
            assertEquals(teacherDtos.get(i).getLastName(), teachers.get(i).getLastName());
            assertEquals(teacherDtos.get(i).getFirstName(), teachers.get(i).getFirstName());
            assertEquals(teacherDtos.get(i).getCreatedAt(), teachers.get(i).getCreatedAt());
            assertEquals(teacherDtos.get(i).getUpdatedAt(), teachers.get(i).getUpdatedAt());
        }
    }

    @Test
    void testToDtoList() {
        // Créer une liste d'objets Teacher à mapper vers une liste d'objets TeacherDto
        List<Teacher> teachers = Arrays.asList(
            new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now()),
            new Teacher(2L, "Smith", "Jane", LocalDateTime.now(), LocalDateTime.now())
        );

        // Mapper la liste d'objets Teacher vers une liste d'objets TeacherDto
        List<TeacherDto> teacherDtos = teacherMapper.toDto(teachers);

        // Vérifier si les champs sont correctement mappés pour chaque objet
        for (int i = 0; i < teachers.size(); i++) {
            assertEquals(teachers.get(i).getId(), teacherDtos.get(i).getId());
            assertEquals(teachers.get(i).getLastName(), teacherDtos.get(i).getLastName());
            assertEquals(teachers.get(i).getFirstName(), teacherDtos.get(i).getFirstName());
            assertEquals(teachers.get(i).getCreatedAt(), teacherDtos.get(i).getCreatedAt());
            assertEquals(teachers.get(i).getUpdatedAt(), teacherDtos.get(i).getUpdatedAt());
        }
    }
}
