package com.gradybward.euclid.elements;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.stream.Stream;

import org.apache.batik.svggen.SVGGraphics2D;

final class Line implements Element {
  private final Point2D.Double p1;
  private final Point2D.Double p2;

  Line(Point2D.Double p1, Point2D.Double p2) {
    this.p1 = p1;
    this.p2 = p2;
  }

  @Override
  public Stream<Double> getBounds() {
    return Stream.of(p1, p1);
  }

  @Override
  public void render(AffineTransform transform, SVGGraphics2D graphics) {
    Stroke originalStroke = graphics.getStroke();
    Point2D.Double a = new Point2D.Double();
    Point2D.Double b = new Point2D.Double();
    transform.transform(p1, a);
    transform.transform(p2, b);
    Point2D.Double farOffA = new Point2D.Double((a.x - b.x) * 1000 + a.x, (a.y - b.y) * 1000 + a.y);
    Point2D.Double farOffB = new Point2D.Double((a.x - b.x) * -1000 + a.x,
        (a.y - b.y) * -1000 + a.y);
    graphics.setStroke(new BasicStroke(1.0f, 0, 0, 0, new float[] { 2f, 2f }, 0f));
    graphics.drawLine((int) farOffA.x, (int) farOffA.y, (int) farOffB.x, (int) farOffB.y);
    graphics.setStroke(originalStroke);
    Rendering.drawPoint(graphics, a);
    Rendering.drawPoint(graphics, b);
  }
}
