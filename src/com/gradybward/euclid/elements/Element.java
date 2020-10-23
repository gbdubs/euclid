package com.gradybward.euclid.elements;

import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Stream;

import org.apache.batik.svggen.SVGGraphics2D;

public interface Element {

  public Stream<Point2D.Double> getBounds();

  public void render(AffineTransform transform, SVGGraphics2D graphics);

  public static Element point(Point2D.Double point) {
    return new Point(point);
  }

  public static Element line(Point2D.Double p1, Point2D.Double p2) {
    return new Line(p1, p2);
  }

  public static PathElement segment(Point2D.Double p1, Point2D.Double p2) {
    return new LineSegment(p1, p2);
  }

  public static Element ray(Point2D.Double p1, Point2D.Double p2) {
    return new Ray(p1, p2);
  }

  public static Element circle(Point2D.Double center, Point2D.Double side) {
    double r = center.distance(side);
    return new Circle(
        new Arc2D.Double(center.x - r, center.y - r, 2 * r, 2 * r, 0, 360, Arc2D.OPEN));
  }

  public static PathElement arcFromAroundToCCW(Point2D.Double from, Point2D.Double around,
      Point2D.Double to) {
    return new Arc(from, around, to, false);
  }

  public static PathElement arcFromAroundToCW(Point2D.Double from, Point2D.Double around,
      Point2D.Double to) {
    return new Arc(from, around, to, true);
  }

  public static Path path(List<PathElement> pathElements) {
    return new Path(pathElements);
  }
}
