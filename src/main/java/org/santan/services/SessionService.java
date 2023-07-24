package org.santan.services;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.santan.entities.Level;
import org.santan.entities.Position;
import org.santan.entities.Session;
import org.santan.entities.User;
import org.santan.repositories.SessionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {
  private final SessionRepository sessionRepository;

  public Session getSessionByUser(User user) {
    return sessionRepository.findByUser(user);
  }

  public List<Session> getAllActiveSessions() {
    return sessionRepository.findAllByActive(true);
  }

  public void deleteSession(Session session) {
    sessionRepository.delete(session);
    sessionRepository.flush();
  }

  public void saveSession(Session session) {
    sessionRepository.save(session);
    sessionRepository.flush();
  }

  public Optional<Session> getSessionById(long id) {
    return sessionRepository.findById(id);
  }

  public boolean isSessionOverdue(Session session) {
    return session.getFinishTimerDate().before(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
  }

  /* Delay before the timer starts (in milliseconds)
   */
  public Duration getDelayDurationMS(Session session) {
    Level level = session.getCurrentLevel();
    Position position = session.getCurrentPosition();

    return level.getPositionTime(position);
  }

}
