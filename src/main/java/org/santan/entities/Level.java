package org.santan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

  public double getPositionTime(Position position) {
    switch (position) {
      case FRONT:
        return frontM;
      case BACK:
        return backM;
      case LEFT_SIDE:
        return leftSideM;
      case RIGHT_SIDE:
        return rightSideM;
      case SHADE:
        return shadeM;
      default:
        return 0;
    }
  }


  public Level(){}
}
