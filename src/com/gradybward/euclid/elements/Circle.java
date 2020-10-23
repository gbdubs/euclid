package com.gradybward.euclid.elements;

import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.util.Arrays;
import java.util.Collection;

import org.apache.batik.svggen.SVGGraphics2D;

final class Circle implements Element {
  private final Arc2D.Double circle;

  Circle(Arc2D.Double circle) {
    this.circle = circle;
  }

  @Override
  public Collection<Double> getBounds() {
    Rectangle2D.Double r = (Rectangle2D.Double) circle.getBounds2D();
    return Arrays.asList(new Point2D.Double(r.getX(), r.getY()),
        new Point2D.Double(r.getX() + r.getWidth(), r.getY()),
        new Point2D.Double(r.getX() + r.getWidth(), r.getY() + r.getHeight()),
        new Point2D.Double(r.getX(), r.getY() + r.getHeight()));
  }

  @Override
  public void render(AffineTransform transform, SVGGraphics2D graphics) {
    graphics.draw(transform.createTransformedShape(circle));
  }
}
