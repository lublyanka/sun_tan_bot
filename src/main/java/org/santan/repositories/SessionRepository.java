package org.santan.repositories;

import org.santan.entities.Session;
import org.santan.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  SessionRepository extends JpaRepository<Session, Long> {
    Session findByUser(User user);

    List<Session> findAllByActive(Boolean isActive);
}
