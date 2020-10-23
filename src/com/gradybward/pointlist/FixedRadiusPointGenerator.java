package com.gradybward.pointlist;

import java.awt.geom.Point2D;

public final class FixedRadiusPointGenerator implements PointGenerator {

  private final double stepSize;

  public FixedRadiusPointGenerator(double stepSize) {
    this.stepSize = stepSize;
  }

  @Override
  public Point2D.Double generate(Point2D.Double last) {
    double theta = Math.random() * Math.PI * 2;
    double dx = stepSize * Math.cos(theta);
    double dy = stepSize * Math.sin(theta);
    return new Point2D.Double(last.x + dx, last.y + dy);
  }
}
