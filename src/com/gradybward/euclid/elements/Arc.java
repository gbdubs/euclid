package com.gradybward.euclid.elements;

import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.stream.Stream;

import org.apache.batik.svggen.SVGGraphics2D;

import com.gradybward.euclid.PointUtils;
import com.gradybward.pointlist.Constraints;

final class Arc implements PathElement {
  private final Point2D.Double from;
  private final Point2D.Double around;
  private final Point2D.Double to;
  private boolean clockwise;

  Arc(Point2D.Double from, Point2D.Double around, Point2D.Double to, boolean clockwise) {
    this.from = from;
    this.around = around;
    this.to = to;
    this.clockwise = clockwise;
  }

  @Override
  public Stream<Point2D.Double> getBounds() {
    return PointUtils.getBoundsFromRectangle((Rectangle2D.Double) getArc().getBounds2D());
  }

  @Override
  public void render(AffineTransform transform, SVGGraphics2D graphics) {
    graphics.draw(transform.createTransformedShape(getArc()));
  }

  @Override
  public Point2D.Double getStart() {
    return from;
  }

  @Override
  public Point2D.Double getEnd() {
    return to;
  }

  @Override
  public PathElement reverse() {
    return new Arc(to, around, from, !clockwise);
  }

  @Override
  public PathIterator getPathIterator() {
    return getArc().getPathIterator(null);
  }

  private Arc2D.Double getArc() {
    double d = from.distance(around);
    // THIS SHOULD NOT WORK... but it actually does generate the arc that connects these two
    // points...
    double start = Math.atan2(around.y - from.y, from.x - around.x) * 180 / Math.PI;
    double end = Math.atan2(around.y - to.y, to.x - around.x) * 180 / Math.PI;
    double extent = end - start;
    if (clockwise) {
      if (extent > 0) {
        extent -= 360;
      }
    } else {
      if (extent < 0) {
        extent += 360;
      }
    }
    return new Arc2D.Double(around.x - d, around.y - d, 2 * d, 2 * d, start, extent, Arc2D.OPEN);
  }
}
