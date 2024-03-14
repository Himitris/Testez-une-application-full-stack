package com.openclassrooms.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.openclassrooms.starterjwt.controllers.SessionController;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.services.SessionService;
import com.openclassrooms.starterjwt.models.Session;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SessionControllerTest {

    @InjectMocks
    private SessionController sessionController;

    @Mock
    private SessionService sessionService;

    @Mock
    private SessionMapper sessionMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        // Given
        long id = 1L;
        Session session = new Session();
        when(sessionService.getById(id)).thenReturn(session);

        // When
        ResponseEntity<?> response = sessionController.findById(String.valueOf(id));

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionMapper, times(1)).toDto(session);
    }

    @Test
    void testFindByIdNotFound() {
        // Given
        long id = 1L;
        when(sessionService.getById(id)).thenReturn(null);

        // When
        ResponseEntity<?> response = sessionController.findById(String.valueOf(id));

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testFindAll() {
        // Given
        List<Session> sessions = new ArrayList<>();
        when(sessionService.findAll()).thenReturn(sessions);

        // When
        ResponseEntity<?> response = sessionController.findAll();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionMapper, times(1)).toDto(sessions);
    }

    @Test
    void testCreate() {
        // Given
        SessionDto sessionDto = new SessionDto();
        Session session = new Session();
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.create(session)).thenReturn(session);

        // When
        ResponseEntity<?> response = sessionController.create(sessionDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionMapper, times(1)).toEntity(sessionDto);
        verify(sessionService, times(1)).create(session);
        verify(sessionMapper, times(1)).toDto(session);
    }

    @Test
    void testUpdate() {
        // Given
        Long sessionId = 1L;
        SessionDto sessionDto = new SessionDto();
        Session session = new Session();
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.update(sessionId, session)).thenReturn(session);

        // When
        ResponseEntity<?> response = sessionController.update("1", sessionDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionMapper, times(1)).toEntity(sessionDto);
        verify(sessionService, times(1)).update(sessionId, session);
        verify(sessionMapper, times(1)).toDto(session);
    }

    @Test
    void testUpdateWithInvalidId() {
        // Given
        String invalidId = "invalid_id";
        SessionDto sessionDto = new SessionDto();

        // When
        ResponseEntity<?> response = sessionController.update(invalidId, sessionDto);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).update(anyLong(), any(Session.class));
        verify(sessionMapper, never()).toEntity(any(SessionDto.class));
        verify(sessionMapper, never()).toDto(any(Session.class));
    }

    @Test
    void testDelete() {
        // Given
        String sessionId = "1";
        when(sessionService.getById(anyLong())).thenReturn(new Session());

        // When
        ResponseEntity<?> response = sessionController.save(sessionId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionService, times(1)).delete(anyLong());
    }

    @Test
    void testDeleteNotFound() {
        // Given
        String sessionId = "1";
        when(sessionService.getById(anyLong())).thenReturn(null);

        // When
        ResponseEntity<?> response = sessionController.save(sessionId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(sessionService, never()).delete(anyLong());
    }

    @Test
    void testDeleteBadRequest() {
        // Given
        String invalidId = "invalid_id";

        // When
        ResponseEntity<?> response = sessionController.save(invalidId);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).delete(anyLong());
    }

    @Test
    void testParticipate() {
        // Given
        String sessionId = "1";
        String userId = "2";

        // When
        ResponseEntity<?> response = sessionController.participate(sessionId, userId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionService, times(1)).participate(anyLong(), anyLong());
    }

    @Test
    void testParticipateBadRequest() {
        // Given
        String invalidSessionId = "invalid_session_id";
        String validUserId = "2";

        // When
        ResponseEntity<?> response = sessionController.participate(invalidSessionId, validUserId);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).participate(anyLong(), anyLong());
    }

    @Test
    void testNoLongerParticipate() {
        // Given
        String sessionId = "1";
        String userId = "2";

        // When
        ResponseEntity<?> response = sessionController.noLongerParticipate(sessionId, userId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sessionService, times(1)).noLongerParticipate(anyLong(), anyLong());
    }

    @Test
    void testNoLongerParticipateBadRequest() {
        // Given
        String validSessionId = "1";
        String invalidUserId = "invalid_user_id";

        // When
        ResponseEntity<?> response = sessionController.noLongerParticipate(validSessionId, invalidUserId);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(sessionService, never()).noLongerParticipate(anyLong(), anyLong());
    }
}
