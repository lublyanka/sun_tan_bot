package org.santan.entities;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sessions")
public class Session {

  @Getter @Setter @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
  @Getter @Setter @ManyToOne Level currentLevel;
  @Getter @Setter @Enumerated(EnumType.STRING) Position currentPosition;
  @Getter @Setter @Column boolean active;
  @Getter @Setter @OneToOne User user;
  @Getter @Setter @Column Timestamp finishTimerDate;
  @Getter @Setter @Column String chatId;
  @Getter @Setter @Column String lang;

  public Session(Level level, Position position, User userToSave, String chatId) {
    this.currentLevel = level;
    this.currentPosition = position;
    this.user = userToSave;
    this.chatId = chatId;
    this.active = true;
    this.lang = "en";
  }

  public Session(){}
}
