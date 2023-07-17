package org.santan.services;

import org.santan.entities.Session;
import org.santan.entities.User;
import org.santan.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {
  @Autowired SessionRepository sessionRepository;

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
}
