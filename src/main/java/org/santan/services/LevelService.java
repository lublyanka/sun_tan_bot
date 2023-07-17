package org.santan.services;

import static org.santan.entities.Position.SHADE;

import lombok.RequiredArgsConstructor;
import org.santan.entities.Level;
import org.santan.entities.Position;
import org.santan.repositories.LevelRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LevelService {
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
}
