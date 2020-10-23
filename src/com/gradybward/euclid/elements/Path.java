package com.gradybward.euclid.elements;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.batik.svggen.SVGGraphics2D;

final class Path implements Element {
  private final List<PathElement> elements;

  Path(List<PathElement> elements) {
    this.elements = elements;
  }

  @Override
  public Collection<Double> getBounds() {
    return this.elements.stream().flatMap(e -> e.getBounds().stream()).collect(Collectors.toList());
  }

  @Override
  public void render(AffineTransform transform, SVGGraphics2D graphics) {
    graphics.draw(transform.createTransformedShape(toPath2D()));
  }

  private Path2D.Double toPath2D() {
    Path2D.Double path = new Path2D.Double();
    Point2D.Double last = getFirstPoint(elements.get(0), elements.get(1));
    for (PathElement element : elements) {
      if (!equals(last, element.getStart())) {
        throw new RuntimeException("Unexpected - not a connected path.");
      }
      path.append(element.getPathIterator(), true);
      last = element.getEnd();
    }
    return path;
  }

  private static Point2D.Double getFirstPoint(PathElement a, PathElement b) {
    if (equals(a.getStart(), b.getStart()) || equals(a.getStart(), b.getEnd())) {
      return a.getEnd();
    } else if (equals(a.getEnd(), b.getStart()) || equals(a.getEnd(), b.getEnd())) {
      return a.getStart();
    }
    throw new RuntimeException("Unexpected - not a connected path.");
  }

  private static boolean equals(Double a, Double b) {
    double allowed = .0001;
    return Math.abs(a.x - b.x) < allowed && Math.abs(a.y - b.y) < allowed;
  }
}
