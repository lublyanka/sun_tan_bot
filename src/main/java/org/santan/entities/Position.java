package org.santan.entities;

import java.util.ArrayList;

public enum Position {
  FRONT,
  BACK,
  LEFT,
  RIGHT,
  SHADE;

  private static final ArrayList<Position> positions =
      new ArrayList<>() {
        {
          add(FRONT);
          add(BACK);
          add(LEFT);
          add(RIGHT);
          add(SHADE);
        }
      };

  public static Position next(Position currentPosition) {
    int index = positions.indexOf(currentPosition);
    if (index == positions.size() - 1) return positions.get(0);
    return positions.get(index + 1);
  }
}
