package com.gradybward.euclid.elements;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.stream.Stream;

import org.apache.batik.svggen.SVGGraphics2D;

final class Point implements Element {

  private final Point2D.Double point;

  Point(Point2D.Double point) {
    this.point = point;
  }

  @Override
  public Stream<Double> getBounds() {
    return Stream.of(point);
  }

  @Override
  public void render(AffineTransform transform, SVGGraphics2D graphics) {
    Point2D.Double t = new Point2D.Double();
    transform.transform(point, t);
    Rendering.drawPoint(graphics, t);
  }
}
