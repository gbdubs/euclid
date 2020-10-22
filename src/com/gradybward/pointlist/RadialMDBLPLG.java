package com.gradybward.pointlist;

import java.awt.geom.Point2D;
import java.util.List;

public final class RadialMDBLPLG extends MinDistanceButLastPLG {

  private final double stepSize;

  public RadialMDBLPLG(Point2D.Double start, List<Point2D.Double> toAvoid, double stepSize,
      double minDistance) {
    super(start, minDistance, toAvoid);
    this.stepSize = stepSize;
  }

  @Override
  public java.awt.geom.Point2D.Double generate(java.awt.geom.Point2D.Double last) {
    double theta = Math.random() * Math.PI * 2;
    double dx = stepSize * Math.cos(theta);
    double dy = stepSize * Math.sin(theta);
    return new Point2D.Double(last.x + dx, last.y + dy);
  }
}
