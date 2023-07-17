package org.santan.repositories;

import java.util.List;
import org.santan.entities.Session;
import org.santan.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
  Session findByUser(User user);

  List<Session> findAllByActive(Boolean isActive);
}
