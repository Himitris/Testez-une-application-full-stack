package com.openclassrooms.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSession() {
        // Given
        Session session = new Session();
        when(sessionRepository.save(session)).thenReturn(session);

        // When
        sessionService.create(session);

        // Then
        verify(sessionRepository, times(1)).save(session);
        // Add more assertions if needed
    }

    @Test
    void testDeleteSession() {
        // Given
        Long sessionId = 1L;

        // When
        sessionService.delete(sessionId);

        // Then
        verify(sessionRepository, times(1)).deleteById(sessionId);
    }

    @Test
    void testFindAllSessions() {
        // Given
        List<Session> sessions = new ArrayList<>();
        when(sessionRepository.findAll()).thenReturn(sessions);

        // When
        List<Session> foundSessions = sessionService.findAll();

        // Then
        verify(sessionRepository, times(1)).findAll();
        assertEquals(sessions, foundSessions);
    }

    @Test
    void testGetSessionById() {
        // Given
        Long sessionId = 1L;
        Session session = new Session();
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // When
        Session foundSession = sessionService.getById(sessionId);

        // Then
        verify(sessionRepository, times(1)).findById(sessionId);
        assertEquals(session, foundSession);
    }

    @Test
    void testUpdateSession() {
        // Given
        Long sessionId = 1L;
        Session session = new Session();
        when(sessionRepository.save(session)).thenReturn(session);

        // When
        Session updatedSession = sessionService.update(sessionId, session);

        // Then
        verify(sessionRepository, times(1)).save(session);
        assertEquals(session, updatedSession);
        assertEquals(sessionId, updatedSession.getId());
    }

    @Test
    void testParticipateInSession() {
        // Given
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        User user = new User();
        session.setId(sessionId);
        List<User> users = new ArrayList<>();
        session.setUsers(users);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        sessionService.participate(sessionId, userId);

        // Then
        assertTrue(session.getUsers().contains(user));
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void testNoLongerParticipateInSession() {
        // Given
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        User user = new User();
        session.setId(sessionId);
        user.setId(userId);
        List<User> users = new ArrayList<>();
        users.add(user);
        session.setUsers(users);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // When
        sessionService.noLongerParticipate(sessionId, userId);

        // Then
        assertFalse(session.getUsers().contains(user));
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void testParticipateInNonexistentSession() {
        // Given
        Long sessionId = 1L;
        Long userId = 1L;
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));
    }

    @Test
    void testParticipateUserNotFound() {
        // Given
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        session.setId(sessionId);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));
    }

    @Test
    void testAlreadyParticipating() {
        // Given
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        User user = new User();
        user.setId(userId);
        session.setId(sessionId);
        List<User> users = new ArrayList<>();
        users.add(user);
        session.setUsers(users);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When, Then
        assertThrows(BadRequestException.class, () -> sessionService.participate(sessionId, userId));
    }

    @Test
    void testNoLongerParticipatingUserNotFound() {
        // Given
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = new Session();
        session.setId(sessionId);
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(2L);
        users.add(user);
        session.setUsers(users);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // When, Then
        assertThrows(BadRequestException.class, () -> sessionService.noLongerParticipate(sessionId, userId));
    }
}
