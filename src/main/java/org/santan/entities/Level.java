package org.santan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Duration;
import lombok.Getter;

@Entity
@Table(name = "levels")
public class Level {

  @Getter @Id int id;
  @Getter @Column double frontM;
  @Getter @Column double backM;
  @Getter @Column double leftSideM;
  @Getter @Column double rightSideM;
  @Getter @Column long shadeM;

  public Level(
      int id, double frontM, double backM, double leftSideM, double rightSideM, long shadeM) {
    this.id = id;
    this.frontM = frontM;
    this.backM = backM;
    this.leftSideM = leftSideM;
    this.rightSideM = rightSideM;
    this.shadeM = shadeM;
  }

  public double getPositionTimeInMinutes(Position position) {
    return switch (position) {
      case FRONT -> frontM;
      case BACK -> backM;
      case LEFT -> leftSideM;
      case RIGHT -> rightSideM;
      case SHADE -> shadeM;
    };
  }

  public Duration getPositionTime(Position position) {
   return Duration.ofSeconds((long)getPositionTimeInMinutes(position)*60);
  }


  public Level(){}
}
