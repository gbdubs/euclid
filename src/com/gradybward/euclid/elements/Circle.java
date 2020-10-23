package com.gradybward.euclid.elements;

import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.stream.Stream;

import org.apache.batik.svggen.SVGGraphics2D;

import com.gradybward.euclid.PointUtils;

final class Circle implements Element {
  private final Arc2D.Double circle;

  Circle(Arc2D.Double circle) {
    this.circle = circle;
  }

  @Override
  public Stream<Double> getBounds() {
    return PointUtils.getBoundsFromRectangle((Rectangle2D.Double) circle.getBounds2D());
  }

  @Override
  public void render(AffineTransform transform, SVGGraphics2D graphics) {
    graphics.draw(transform.createTransformedShape(circle));
  }
}
