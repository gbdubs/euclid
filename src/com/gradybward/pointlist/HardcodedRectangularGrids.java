package com.gradybward.pointlist;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HardcodedRectangularGrids {
  public static List<Point2D.Double> topToBottom(int height, int width) {
    List<Point2D.Double> result = new ArrayList<>();
    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        int actualw = h % 2 == 0 ? w : width - w - 1;
        result.add(new Point2D.Double(h, actualw));
      }
    }
    return result;
  }
  public static List<Point2D.Double> topLeftCorner(int dimension) {
    int height = dimension;
    int width = dimension;
    List<Point2D.Double> result = new ArrayList<>();
    int h = 0;
    int w = 0;
    boolean goingHorizontal = false;
    boolean goingUp = true;
    while (h <= height && w <= width) {
      result.add(new Point2D.Double(h, w));
      if (h == w) {
        goingHorizontal = !goingHorizontal;
        goingUp = true;
      }
      if (goingHorizontal) {
        if (goingUp) {
          w++;
        } else {
          w--;
        }
      } else {
        if (goingUp) {
          h++;
        } else {
          h--;
        }
      }
      if (h == height && w <= width) {
        result.add(new Point2D.Double(h, w));
        w++;
        goingUp = false;
      } else if (w == width && h <= height) {
        result.add(new Point2D.Double(h, w));
        h++;
        goingUp = false;
      }
    }
    return result;
  }

  public static List<Point2D.Double> topRightCorner(int dimension) {
    return topLeftCorner(dimension).stream().map(p -> new Point2D.Double(dimension - p.x, p.y)).collect(Collectors.toList());
  }
  public static List<Point2D.Double> bottomRightCorner(int dimension) {
    return topLeftCorner(dimension).stream().map(p -> new Point2D.Double(dimension - p.x, dimension - p.y)).collect(Collectors.toList());
  }
  public static List<Point2D.Double> bottomLeftCorner(int dimension) {
    return topLeftCorner(dimension).stream().map(p -> new Point2D.Double(p.x, dimension - p.y)).collect(Collectors.toList());
  }
  public static List<Point2D.Double> translate(Point2D.Double origin, List<Point2D.Double> ps) {
    return ps.stream().map(p -> new Point2D.Double(p.x + origin.x, p.y + origin.y)).collect(Collectors.toList());
  }
  public static List<Point2D.Double> combo(int size) {
    List<Point2D.Double> result = new ArrayList<>();
    result.addAll(bottomLeftCorner(size));
  /*  List<Point2D.Double> next = translate(new Point2D.Double(0, 4), bottomLeftCorner(size));
    next.remove(0);
    result.addAll(next); */
    return result; 
  }
}
