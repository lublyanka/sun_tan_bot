package org.santan.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PositionTest {

  @Test
  void testNext() {
      Position position = Position.FRONT;
      assertEquals(Position.BACK, Position.next(position));
      position = Position.SHADE;
      assertEquals(Position.FRONT, Position.next(position));
  }
}