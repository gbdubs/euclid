package com.gradybward.euclid.elements;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.batik.svggen.SVGGraphics2D;

public final class Path implements Element {
  private final List<PathElement> elements;

  Path(List<PathElement> elements) {
    this.elements = elements;
  }

  @Override
  public Stream<Double> getBounds() {
    return this.elements.stream().flatMap(Element::getBounds);
  }

  @Override
  public void render(AffineTransform transform, SVGGraphics2D graphics) {
    graphics.draw(transform.createTransformedShape(toPath2D()));
  }

  public Path2D.Double toPath2D() {
    Path2D.Double path = new Path2D.Double();
    Point2D.Double last = getStart();
    for (PathElement element : elements) {
      if (!equals(last, element.getStart())) {
        throw new RuntimeException("Unexpected - not a connected path.");
      }
      path.append(element.getPathIterator(), true);
      last = element.getEnd();
    }
    return path;
  }
  
  public Point2D.Double getStart() {
    return getEndPoint(elements.get(0), elements.get(1));
  }
  
  public Point2D.Double getEnd() {
    return getEndPoint(elements.get(elements.size() - 1), elements.get(elements.size() - 2));
  }
  
  public Path reverse() {
    List<PathElement> reversed = new ArrayList<>();
    for (int i = elements.size() - 1; i >= 0; i--) {
      reversed.add(elements.get(i).reverse());
    }
    return new Path(reversed);
  }

  private static Point2D.Double getEndPoint(PathElement a, PathElement b) {
    if (equals(a.getStart(), b.getStart()) || equals(a.getStart(), b.getEnd())) {
      return a.getEnd();
    } else if (equals(a.getEnd(), b.getStart()) || equals(a.getEnd(), b.getEnd())) {
      return a.getStart();
    }
    throw new RuntimeException(String.format(
        "Unexpected - not a connected path.\nPathElementA = %s = [%s, %s]\nPathElementB = %s = [%s, %s]\n", a, a.getStart(), a.getEnd(), b, b.getStart(), b.getEnd()));
  }

  private static boolean equals(Double a, Double b) {
    double allowed = .0001;
    return Math.abs(a.x - b.x) < allowed && Math.abs(a.y - b.y) < allowed;
  }
}
