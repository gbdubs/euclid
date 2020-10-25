package com.gradybward.pointlist;

import java.awt.geom.Point2D;

public class TriangularGridRandomPointGenerator implements PointGenerator {
  
  private final int maxHop;
  
  public TriangularGridRandomPointGenerator(int maxHop) {
    this.maxHop = maxHop;
  }
  
  @Override
  public Point2D.Double generate(Point2D.Double last) {
    double magnitude = 1 + Math.round(Math.random() * (maxHop - 1));
    double angle = Math.round(Math.random() * 6) * Math.PI / 3;
    double dx = magnitude * Math.cos(angle);
    double dy = magnitude * Math.sin(angle);
    Point2D.Double result = new Point2D.Double(last.x + dx, last.y + dy);
    return result;
  }
}
