package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class SessionMapperTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToEntity() {
        // Given
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Test session");
        sessionDto.setDate(new Date());
        sessionDto.setDescription("Test description");
        sessionDto.setTeacher_id(1L);

        Session expectedSession = new Session();
        expectedSession.setName("Test session");
        expectedSession.setDate(sessionDto.getDate());
        expectedSession.setDescription("Test description");
        expectedSession.setTeacher(new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now()));

        when(teacherService.findById(sessionDto.getTeacher_id())).thenReturn(new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now()));

        // When
        Session result = sessionMapper.toEntity(sessionDto);

        // Then
        assertEquals(expectedSession.getName(), result.getName());
        assertEquals(expectedSession.getDate(), result.getDate());
        assertEquals(expectedSession.getDescription(), result.getDescription());
        assertEquals(expectedSession.getTeacher().getId(), result.getTeacher().getId());
    }

    @Test
    void testToDto() {
        // Given
        Session session = new Session();
        session.setName("Test session");
        session.setDate(new Date());
        session.setDescription("Test description");
        session.setTeacher(new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now()));

        SessionDto expectedSessionDto = new SessionDto();
        expectedSessionDto.setName("Test session");
        expectedSessionDto.setDate(new Date());
        expectedSessionDto.setDescription("Test description");
        expectedSessionDto.setTeacher_id(1L);

        // When
        SessionDto result = sessionMapper.toDto(session);

        // Then
        assertEquals(expectedSessionDto.getName(), result.getName());
        //assertEquals(expectedSessionDto.getDate(), result.getDate());
        assertEquals(expectedSessionDto.getDescription(), result.getDescription());
        assertEquals(expectedSessionDto.getTeacher_id(), result.getTeacher_id());
    }

    @Test
    void testToDtoList() {
        // Given
        List<Session> sessionList = Arrays.asList(
                new Session(1L, "Session 1", new Date(), "Description 1", null, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now()),
                new Session(2L, "Session 2", new Date(), "Description 2", null, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now())
        );

        // When
        List<SessionDto> result = sessionMapper.toDto(sessionList);

        // Then
        assertEquals(sessionList.size(), result.size());
        for (int i = 0; i < sessionList.size(); i++) {
            assertEquals(sessionList.get(i).getName(), result.get(i).getName());
            assertEquals(sessionList.get(i).getDate(), result.get(i).getDate());
            assertEquals(sessionList.get(i).getDescription(), result.get(i).getDescription());
        }
    }

    @Test
    void testToEntityList() {
        // Given
        List<SessionDto> sessionDtoList = Arrays.asList(
                new SessionDto(1L, "Session 1", new Date(), 1L, "Description 1", null, LocalDateTime.now(), LocalDateTime.now()),
                new SessionDto(2L, "Session 2", new Date(), 2L, "Description 2", null, LocalDateTime.now(), LocalDateTime.now())
        );

        // When
        List<Session> result = sessionMapper.toEntity(sessionDtoList);

        // Then
        assertEquals(sessionDtoList.size(), result.size());
        for (int i = 0; i < sessionDtoList.size(); i++) {
            assertEquals(sessionDtoList.get(i).getName(), result.get(i).getName());
            assertEquals(sessionDtoList.get(i).getDate(), result.get(i).getDate());
            assertEquals(sessionDtoList.get(i).getDescription(), result.get(i).getDescription());
        }
    }
}
