package com.gradybward.euclid;

import java.awt.geom.Point2D;

public final class PointUtils {

  public static Point2D.Double pointFromLinePlusDistance(Point2D.Double p1, Point2D.Double p2,
      double distance) {
    double factor = distance / p1.distance(p2);
    return new Point2D.Double(p1.x + (p2.x - p1.x) * factor, p1.y + (p2.y - p1.y) * factor);
  }

  private PointUtils() {}
}
