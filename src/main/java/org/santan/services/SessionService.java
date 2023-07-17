package org.santan.services;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
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

  public Optional<Session> getSessionById(long id){
    return sessionRepository.findById(id);
  }
}
