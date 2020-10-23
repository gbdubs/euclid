package com.gradybward.euclid;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.stream.Stream;

public final class PointUtils {

  public static Point2D.Double pointFromLinePlusDistance(Point2D.Double p1, Point2D.Double p2,
      double distance) {
    double factor = distance / p1.distance(p2);
    return new Point2D.Double(p1.x + (p2.x - p1.x) * factor, p1.y + (p2.y - p1.y) * factor);
  }

  public static Stream<Point2D.Double> getBoundsFromRectangle(Rectangle2D.Double r) {
    return Stream.of(new Point2D.Double(r.getX(), r.getY()),
        new Point2D.Double(r.getX() + r.getWidth(), r.getY()),
        new Point2D.Double(r.getX() + r.getWidth(), r.getY() + r.getHeight()),
        new Point2D.Double(r.getX(), r.getY() + r.getHeight()));
  }
  
  private PointUtils() {}
}
