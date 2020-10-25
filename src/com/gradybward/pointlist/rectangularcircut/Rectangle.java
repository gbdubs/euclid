package com.gradybward.pointlist.rectangularcircut;

import java.util.Arrays;
import java.util.List;

public class Rectangle {
  
  final Point topLeft;
  final int h;
  final int w;
  Point in;
  Point out;
  
  public Rectangle(Point topLeft, int h, int w) {
    this.topLeft = topLeft;
    this.h = h;
    this.w = w;
  }

  public List<Point> getCorners() {
    return Arrays.asList(topLeft, new Point(topLeft.x + w, topLeft.y), new Point(topLeft.x + w, topLeft.y + h), new Point(topLeft.x, topLeft.y + h));
  }
  
  public List<Point> getCommonCorners(Rectangle other) {
    List<Point> thisCorners = getCorners();
    thisCorners.retainAll(other.getCorners());
    return thisCorners;
  }
  
  public boolean hasCommonCorner(Rectangle other) {
    return !getCommonCorners(other).isEmpty();
  }
}
