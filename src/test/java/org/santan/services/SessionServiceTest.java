package org.santan.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.santan.entities.Level;
import org.santan.entities.Position;
import org.santan.entities.Session;
import org.santan.entities.User;
import org.santan.repositories.SessionRepository;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class SessionServiceTest {
    SessionRepository sessionRepository = Mockito.mock(SessionRepository.class);
    SessionService sessionService = new SessionService(sessionRepository);
    @Test
    void getSessionByUser() {
        User user = new User();
        user.setId(1L);
        Session session = new Session();
        when(sessionRepository.findByUser(user)).thenReturn(session);
        Session result = sessionService.getSessionByUser(user);
        assertEquals(session, result);
      }

    @Test
    void getAllActiveSessions() {
        List<Session> sessionList = new ArrayList<>();
        when(sessionRepository.findAllByActive(true)).thenReturn(sessionList);
        List<Session> result =  sessionService.getAllActiveSessions();
        assertEquals(sessionList, result);

      }

    @Test
    void deleteSession() {
        Session session = new Session();
        sessionService.deleteSession(session);
        verify(sessionRepository,times(1)).delete(eq(session));
        verify(sessionRepository,times(1)).flush();
      }

    @Test
    void saveSession() {
    Session session = new Session();
        sessionService.saveSession(session);
        verify(sessionRepository,times(1)).save(eq(session));
        verify(sessionRepository,times(1)).flush();
      }

    @Test
    void getSessionById() {
        Session session = new Session();
        session.setId(1L);
        Optional<Session> optionalSession = Optional.of(session);
        when(sessionRepository.findById(session.getId())).thenReturn(optionalSession);
        Optional <Session> result = sessionService.getSessionById(session.getId());
        assertEquals(optionalSession, result);
    }

  @Test
  void isSessionOverdue_True() {
        Session session = new Session();
        session.setFinishTimerDate(Timestamp.valueOf(LocalDateTime.now().minusDays(1L)));
        assertTrue(sessionService.isSessionOverdue(session));
  }

    @Test
    void isSessionOverdue_False() {
        Session session = new Session();
        session.setFinishTimerDate(Timestamp.from(Instant.now()));
        assertFalse(sessionService.isSessionOverdue(session));
    }

    @Test
    void isSessionOverdue_FalseAtStartOfaDay() {
        Session session = new Session();
        session.setFinishTimerDate(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
        assertFalse(sessionService.isSessionOverdue(session));
    }

  @Test
  void getDelayMS() {
        Session session = new Session();
      Level level = new Level(1,1,1,1,1,1);
      Position position = Position.RIGHT;
      session.setCurrentPosition(position);
      session.setCurrentLevel(level);
      Duration duration = sessionService.getDelayDurationMS(session);
      assertEquals(Duration.ofSeconds(60L), duration);
  }
}