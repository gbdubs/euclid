package com.gradybward.pointlist.rectangularcircut;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.gradybward.gridtraversals.rectangles.RectangularGridTraversal;

public class Rectangle {

  final Point2D.Double topLeft;
  final int w;
  final int h;
  Point2D.Double in;
  Point2D.Double out;

  public Rectangle(Point2D.Double topLeft, int w, int h) {
    this.topLeft = topLeft;
    this.w = w;
    this.h = h;
  }

  public List<Point2D.Double> getCorners() {
    return Arrays.asList(
        new Point2D.Double(topLeft.x, topLeft.y), 
        new Point2D.Double(topLeft.x + w - 1, topLeft.y),
        new Point2D.Double(topLeft.x + w - 1, topLeft.y + h - 1), 
        new Point2D.Double(topLeft.x, topLeft.y + h - 1));
  }

  public List<Point2D.Double> getCornersAdjacentToOtherRectanglesCorners(Rectangle other) {
    List<Point2D.Double> thisCorners = new ArrayList<>();
    for (Point2D.Double thisCorner : getCorners()) {
      for (Point2D.Double otherCorner : other.getCorners()) {
        if (thisCorner.distance(otherCorner) < 1.01) {
          thisCorners.add(thisCorner);
        }
      }
    }
    return thisCorners;
  }
  
  public boolean hasCornerAdjacentToCornerInOtherRectangle(Rectangle other) {
    return !getCornersAdjacentToOtherRectanglesCorners(other).isEmpty();
  }
  
  public Optional<Point2D.Double> getAdjacentCorner(Point2D.Double other) {
    for (Point2D.Double corner : getCorners()) {
      if (corner.distance(other) < 1.01) {
        return Optional.of(corner);
      }
    }
    return Optional .empty();
  }
  
  @Override
  public String toString() {
    return String.format("WxH=%dx%d In=%s Out=%s TopLeft=%s", w, h, in, out, topLeft);
  }

  public void validateGeneratedGrid(RectangularGridTraversal grid) {
    List<Point2D.Double> points = grid.get();
    if (points.get(0).distance(in) > .01) {
      throw new RuntimeException(String.format("Grid failed do meet IN criterion. Expected %s, was %s", in, points.get(0)));
    }
    if (points.get(points.size() - 1).distance(out) > .01) {
      throw new RuntimeException(String.format("Grid failed do meet OUT criterion. Expected %s, was %s", out, points.get(points.size() - 1)));
    }
  }
}
