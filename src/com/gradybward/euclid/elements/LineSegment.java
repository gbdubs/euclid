package com.gradybward.euclid.elements;

import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Arrays;
import java.util.Collection;

import org.apache.batik.svggen.SVGGraphics2D;

final class LineSegment implements PathElement {

  private final Point2D.Double p1;
  private final Point2D.Double p2;

  LineSegment(Point2D.Double p1, Point2D.Double p2) {
    this.p1 = p1;
    this.p2 = p2;
  }

  @Override
  public Collection<Double> getBounds() {
    return Arrays.asList(p1, p1);
  }

  @Override
  public void render(AffineTransform transform, SVGGraphics2D graphics) {
    Stroke originalStroke = graphics.getStroke();
    Point2D.Double a = new Point2D.Double();
    Point2D.Double b = new Point2D.Double();
    transform.transform(p1, a);
    transform.transform(p2, b);
    graphics.setStroke(originalStroke);
    graphics.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
    Rendering.drawPoint(graphics, a);
    Rendering.drawPoint(graphics, b);
  }

  @Override
  public Point2D.Double getStart() {
    return p1;
  }

  @Override
  public Point2D.Double getEnd() {
    return p2;
  }

  @Override
  public PathElement reverse() {
    return new LineSegment(p2, p1);
  }

  @Override
  public PathIterator getPathIterator() {
    return new Line2D.Double(p1, p2).getPathIterator(null);
  }
}
