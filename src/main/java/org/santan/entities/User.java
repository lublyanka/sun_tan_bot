package org.santan.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {

  @Getter @Setter @Id long id;
  @Getter @Setter @ManyToOne Level currentLevel;
  @Getter @Setter @Enumerated(EnumType.STRING) Position currentPosition;

  public User(long id, Level currentLevel, Position currentPosition) {
    this.id = id;
    this.currentLevel = currentLevel;
    this.currentPosition = currentPosition;
  }
/*
  public User(Level currentLevel, Position currentPosition) {
    this.currentLevel = currentLevel;
    this.currentPosition = currentPosition;
  }

  public User(Level currentLevel, Position currentPosition, boolean firstDay) {
    this.currentLevel = currentLevel;
    this.currentPosition = currentPosition;
  }*/

  public User() {
  }
}
