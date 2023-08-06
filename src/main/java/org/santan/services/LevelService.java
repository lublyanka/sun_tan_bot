package org.santan.services;

import static org.santan.entities.Position.FRONT;
import static org.santan.entities.Position.SHADE;

import lombok.RequiredArgsConstructor;
import org.santan.entities.Level;
import org.santan.entities.Position;
import org.santan.entities.Session;
import org.santan.repositories.LevelRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LevelService {
  public static final int LEVEL_REDUCTION = 2;
  public static final int MAX_LEVEL = 12;
  public static final int MIN_LEVEL = 1;
  private final LevelRepository levelRepository;

  public Level next(Level currentLevel, Position position) {
    if (currentLevel == null) return levelRepository.findById(1).get();
    if (currentLevel.getId() == 12 && position.equals(SHADE))
      return levelRepository.findById(1).get();
    if (position.equals(SHADE)) return levelRepository.findById(currentLevel.getId() + 1).get();
    return currentLevel;
  }

  public Level getFirstLevel() {
    return levelRepository.findById(1).get();
  }

  public Level getLevelById(int levelId) {
    return levelRepository.findById(levelId).get();
  }

  public Session getSessionWithLevelReduction(Session session) {
    Level level = getLevelById(session.getCurrentLevel().getId());
    level = getLevelReduction(session, level);

    session.setCurrentLevel(level);
    session.setCurrentPosition(FRONT);
    return session;
  }

  private Level getLevelReduction(Session session, Level level) {
    if (level.getId() <= MIN_LEVEL + 1) {
      level = getFirstLevel();
    } else {
      level = getLevelById(session.getCurrentLevel().getId() - LEVEL_REDUCTION);
    }
    return level;
  }
}
