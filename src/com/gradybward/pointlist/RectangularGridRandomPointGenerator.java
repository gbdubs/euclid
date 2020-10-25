package com.gradybward.pointlist;

import java.awt.geom.Point2D;

public class RectangularGridRandomPointGenerator implements PointGenerator {

  private final int maxHopX;
  private final int maxHopY;
  
  public RectangularGridRandomPointGenerator(int maxHopX, int maxHopY) {
    this.maxHopX = maxHopX;
    this.maxHopY = maxHopY;
  }
  
  @Override
  public Point2D.Double generate(Point2D.Double last) {
    int dx = (int) Math.round(Math.random() * maxHopX * 2) - maxHopX;
    int dy = (int) Math.round(Math.random() * maxHopY * 2) - maxHopY;
    return new Point2D.Double(last.x + dx, last.y + dy);
  }
}
