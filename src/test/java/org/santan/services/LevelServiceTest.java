package org.santan.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.santan.entities.Level;
import org.santan.entities.Position;
import org.santan.entities.Session;
import org.santan.repositories.LevelRepository;

class LevelServiceTest {

  LevelRepository levelRepository = Mockito.mock(LevelRepository.class);
  LevelService levelService = new LevelService(levelRepository);

  @Test
  void next_FirstLevel() {
    Integer levelId = 1;
    Level level = new Level();
    when(levelRepository.findById(levelId)).thenReturn(Optional.of(level));
    Level result = levelService.next(null, Position.FRONT);
    assertEquals(level, result);
  }

  @Test
  void next_LastLevelPositionShade() {
    Integer levelId = 1;
    Level level12 = new Level(12, 12, 12, 12, 12, 12);
    Level level1 = new Level(1, 1, 1, 1, 1, 1);
    when(levelRepository.findById(levelId)).thenReturn(Optional.of(level1));
    Level result = levelService.next(level12, Position.SHADE);
    assertEquals(level1, result);
  }

  @Test
  void next_PositionShade() {
    int levelId = 1;
    Level level1 = new Level(1, 1, 1, 1, 1, 1);
    Level level2 = new Level(2, 2, 2, 2, 2, 2);
    when(levelRepository.findById(levelId)).thenReturn(Optional.of(level1));
    when(levelRepository.findById(levelId + 1)).thenReturn(Optional.of(level2));
    Level result = levelService.next(level1, Position.SHADE);
    assertEquals(level2, result);
  }

  @Test
  void next_Ordinary() {
    Level level = new Level(3, 3, 3, 3, 3, 3);
    Level result = levelService.next(level, Position.LEFT);
    assertEquals(level, result);
  }

  @Test
  void getFirstLevel() {
    Integer levelId = 1;
    Level level = new Level();
    when(levelRepository.findById(levelId)).thenReturn(Optional.of(level));
    Level result = levelService.getFirstLevel();
    assertEquals(level, result);
  }

  @Test
  void getLevelById() {
    int levelId = 1;
    Level level = new Level();
    when(levelRepository.findById(levelId)).thenReturn(Optional.of(level));
    Level result = levelService.getLevelById(levelId);
    assertEquals(level, result);
  }

  @Test
  void getSessionWithLevelReduction_1Level() {
    Session session = new Session();
    Level  level = new Level(1,1,1,1,1,1);
    session.setCurrentLevel(level);
    when(levelRepository.findById(1)).thenReturn(Optional.of(level));

    Session result = levelService.getSessionWithLevelReduction(session);

    assertEquals(session, result);
  }

  @Test
  void getSessionWithLevelReduction_2Level() {
    Session session = new Session();
    Level  level2 = new Level(2,2,2,2,2,2);
    session.setCurrentLevel(level2);
    Session sessionResults = new Session();
    Level  level1 = new Level(1,1,1,1,1,1);
    sessionResults.setCurrentLevel(level1);
    sessionResults.setCurrentPosition(Position.FRONT);
    when(levelRepository.findById(1)).thenReturn(Optional.of(level1));
    when(levelRepository.findById(2)).thenReturn(Optional.of(level2));

    Session result = levelService.getSessionWithLevelReduction(session);

    assertEquals(sessionResults.getCurrentLevel(), result.getCurrentLevel());
    assertEquals(sessionResults.getCurrentPosition(), result.getCurrentPosition());
  }

  @Test
  void getSessionWithLevelReduction_12Level() {
    Session session = new Session();
    Level  level12 = new Level(12,12,12,12,12,12);
    session.setCurrentLevel(level12);
    Session sessionResults = new Session();
    Level  level10 = new Level(10,10,10,10,10,10);
    sessionResults.setCurrentLevel(level10);
    sessionResults.setCurrentPosition(Position.FRONT);
    when(levelRepository.findById(10)).thenReturn(Optional.of(level10));
    when(levelRepository.findById(12)).thenReturn(Optional.of(level12));

    Session result = levelService.getSessionWithLevelReduction(session);

    assertEquals(sessionResults.getCurrentLevel(), result.getCurrentLevel());
    assertEquals(sessionResults.getCurrentPosition(), result.getCurrentPosition());
  }

}
