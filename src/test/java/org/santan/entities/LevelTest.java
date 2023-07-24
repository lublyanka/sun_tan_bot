package org.santan.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LevelTest {

  @Test
  void getPositionTimeInMinutes() {

    Level level = new Level(1, 2, 3, 4, 5, 6);
    Position position = Position.FRONT;
    double timeInMinutes = level.getPositionTimeInMinutes(position);
    assertEquals(level.getFrontM(), timeInMinutes);

    position = Position.BACK;
    timeInMinutes = level.getPositionTimeInMinutes(position);
    assertEquals(level.getBackM(), timeInMinutes);

    position = Position.LEFT;
    timeInMinutes = level.getPositionTimeInMinutes(position);
    assertEquals(level.getLeftSideM(), timeInMinutes);

    position = Position.RIGHT;
    timeInMinutes = level.getPositionTimeInMinutes(position);
    assertEquals(level.getRightSideM(), timeInMinutes);

    position = Position.SHADE;
    timeInMinutes = level.getPositionTimeInMinutes(position);
    assertEquals(level.getShadeM(), timeInMinutes);
  }
}
