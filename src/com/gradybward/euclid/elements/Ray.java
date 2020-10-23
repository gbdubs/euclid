package com.gradybward.euclid.elements;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Arrays;
import java.util.Collection;

import org.apache.batik.svggen.SVGGraphics2D;

final class Ray implements Element {

  private final Point2D.Double origin;
  private final Point2D.Double destination;

  Ray(Point2D.Double origin, Point2D.Double destination) {
    this.origin = origin;
    this.destination = destination;
  }

  @Override
  public Collection<Double> getBounds() {
    return Arrays.asList(origin, destination);
  }

  @Override
  public void render(AffineTransform transform, SVGGraphics2D graphics) {
    Stroke originalStroke = graphics.getStroke();
    Point2D.Double tOrigin = new Point2D.Double();
    Point2D.Double tDestination = new Point2D.Double();
    transform.transform(origin, tOrigin);
    transform.transform(destination, tDestination);
    Point2D.Double farOff = new Point2D.Double((tDestination.x - tOrigin.x) * 1000 + tOrigin.x,
        (tDestination.y - tOrigin.y) * 1000 + tOrigin.y);
    graphics.setStroke(new BasicStroke(1.0f, 0, 0, 0, new float[] { 2f, 2f }, 0f));
    graphics.drawLine((int) tDestination.x, (int) tDestination.y, (int) farOff.x, (int) farOff.y);
    graphics.setStroke(originalStroke);
    graphics.drawLine((int) tDestination.x, (int) tDestination.y, (int) tOrigin.x, (int) tOrigin.y);
    Rendering.drawPoint(graphics, tOrigin);
    Rendering.drawPoint(graphics, tDestination);
  }
}
