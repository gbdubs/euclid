package com.gradybward.pointlist;

import java.awt.geom.Point2D;

public final class RandomRadiusPointGenerator implements PointGenerator {

  private final double stepSizeMean;
  private final double stepSizeRange;

  public RandomRadiusPointGenerator(double stepSizeMean, double stepSizeRange) {
    this.stepSizeMean = stepSizeMean;
    this.stepSizeRange = stepSizeRange;
  }

  @Override
  public Point2D.Double generate(Point2D.Double last) {
    double stepSize = stepSizeMean + (Math.random() - .5) * stepSizeRange;
    double theta = Math.random() * Math.PI * 2;
    double dx = stepSize * Math.cos(theta);
    double dy = stepSize * Math.sin(theta);
    return new Point2D.Double(last.x + dx, last.y + dy);
  }
}
