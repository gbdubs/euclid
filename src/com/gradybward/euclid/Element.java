package com.gradybward.euclid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Collection;

import org.apache.batik.svggen.SVGGraphics2D;

/* A translation from a awt.geom object to a printable one. */
public interface Element {

  public Collection<Point2D.Double> getBounds();

  public void render(AffineTransform transform, SVGGraphics2D graphics);

  public static Point point(Point2D.Double point) {
    return new Point(point);
  }

  public static Line line(Point2D.Double p1, Point2D.Double p2) {
    return new Line(p1, p2);
  }

  public static LineSegment segment(Point2D.Double p1, Point2D.Double p2) {
    return new LineSegment(p1, p2);
  }

  public static Ray ray(Point2D.Double p1, Point2D.Double p2) {
    return new Ray(p1, p2);
  }

  public static Circle circle(Point2D.Double p1, Point2D.Double p2) {
    return new Circle(circleFromOriginAndSide(p1, p2));
  }

  public static Arc arcFromAroundToCCW(Point2D.Double from, Point2D.Double around,
      Point2D.Double to) {
    return new Arc(arcFromAroundToCClockwise(from, around, to));
  }

  public static Arc arcFromAroundToCW(Point2D.Double from, Point2D.Double around,
      Point2D.Double to) {
    return new Arc(arcFromAroundToCClockwise(to, around, from));
  }

  public final class Point implements Element {
    private final Point2D.Double point;

    private Point(Point2D.Double point) {
      this.point = point;
    }

    @Override
    public Collection<Double> getBounds() {
      return Arrays.asList(point);
    }

    @Override
    public void render(AffineTransform transform, SVGGraphics2D graphics) {
      Point2D.Double t = new Point2D.Double();
      transform.transform(point, t);
      drawPoint(graphics, t);
    }
  }

  public class Line implements Element {
    private final Point2D.Double p1;
    private final Point2D.Double p2;

    private Line(Point2D.Double p1, Point2D.Double p2) {
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
      Point2D.Double farOffA = new Point2D.Double((a.x - b.x) * 1000 + a.x,
          (a.y - b.y) * 1000 + a.y);
      Point2D.Double farOffB = new Point2D.Double((a.x - b.x) * -1000 + a.x,
          (a.y - b.y) * -1000 + a.y);
      graphics.setStroke(new BasicStroke(1.0f, 0, 0, 0, new float[] { 2f, 2f }, 0f));
      graphics.drawLine((int) farOffA.x, (int) farOffA.y, (int) farOffB.x, (int) farOffB.y);
      graphics.setStroke(originalStroke);
      drawPoint(graphics, a);
      drawPoint(graphics, b);
    }
  }

  public class LineSegment implements Element {
    private final Point2D.Double p1;
    private final Point2D.Double p2;

    private LineSegment(Point2D.Double p1, Point2D.Double p2) {
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
      drawPoint(graphics, a);
      drawPoint(graphics, b);
    }
  }

  public class Ray implements Element {
    private final Point2D.Double origin;
    private final Point2D.Double destination;

    private Ray(Point2D.Double origin, Point2D.Double destination) {
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
      graphics.drawLine((int) tDestination.x, (int) tDestination.y, (int) tOrigin.x,
          (int) tOrigin.y);
      drawPoint(graphics, tOrigin);
      drawPoint(graphics, tDestination);
    }
  }

  public class Circle implements Element {
    private final Arc2D.Double circle;

    private Circle(Arc2D.Double circle) {
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

  public class Arc implements Element {
    private final Arc2D.Double arc;

    private Arc(Arc2D.Double arc) {
      this.arc = arc;
    }

    @Override
    public Collection<Double> getBounds() {
      Rectangle2D.Double r = (Rectangle2D.Double) arc.getBounds2D();
      return Arrays.asList(new Point2D.Double(r.getX(), r.getY()),
          new Point2D.Double(r.getX() + r.getWidth(), r.getY()),
          new Point2D.Double(r.getX() + r.getWidth(), r.getY() + r.getHeight()),
          new Point2D.Double(r.getX(), r.getY() + r.getHeight()));
    }

    @Override
    public void render(AffineTransform transform, SVGGraphics2D graphics) {
      graphics.draw(transform.createTransformedShape(arc));
    }
  }

  static void drawPoint(SVGGraphics2D graphics, Point2D.Double p) {
    int radius = 3;
    Color initialColor = graphics.getColor();
    graphics.setColor(Color.WHITE);
    graphics.fillArc((int) p.x - radius, (int) p.y - radius, radius * 2, radius * 2, 0, 360);
    graphics.setColor(initialColor);
    graphics.drawArc((int) p.x - radius, (int) p.y - radius, radius * 2, radius * 2, 0, 360);
  }

  static Arc2D.Double circleFromOriginAndSide(Point2D.Double center, Point2D.Double side) {
    double r = center.distance(side);
    return new Arc2D.Double(center.x - r, center.y - r, 2 * r, 2 * r, 0, 360, Arc2D.OPEN);
  }

  static Arc2D.Double arcFromAroundToCClockwise(Point2D.Double from, Point2D.Double around,
      Point2D.Double to) {
    double d = from.distance(around);
    // THIS SHOULD NOT WORK... but it actually does generate the arc that connects these two
    // points...
    double start = Math.atan2(around.y - from.y, from.x - around.x) * 180 / Math.PI;
    double end = Math.atan2(around.y - to.y, to.x - around.x) * 180 / Math.PI;
    if (end < start) {
      end += 360;
    }
    return new Arc2D.Double(around.x - d, around.y - d, 2 * d, 2 * d, start, end - start,
        Arc2D.OPEN);
  }
}
