package com.gradybward.pointlist;

import java.awt.geom.Point2D;

public class RectangularGridSnakePointGenerator implements PointGenerator {

  private final int maxX;
  private final int maxY;
  private final boolean horizontal;
  
  public RectangularGridSnakePointGenerator(int maxX, int maxY, boolean horizontal) {
    this.maxX = maxX;
    this.maxY = maxY;
    this.horizontal = horizontal;
  }
  
  @Override
  public Point2D.Double generate(Point2D.Double last) {
    if (last.x > maxX || last.y > maxY || (last.x == maxX && last.y == maxY)) {
      throw new RuntimeException("don't generate more than this can handle!");
    }
    if (horizontal) {
      boolean evenRow = (int) last.y % 2 == 0;
      if ((last.x == maxX && evenRow) || (last.x == 0 && !evenRow)) {
        return new Point2D.Double(last.x, last.y + 1);
      }
      if (evenRow) {
        return new Point2D.Double(last.x + 1, last.y);
      }
      return new Point2D.Double(last.x - 1, last.y);
    }
    boolean evenCol = (int) last.x % 2 == 0;
    if ((last.y == maxY && evenCol) || (last.y == 0 && !evenCol)) {
      return new Point2D.Double(last.x + 1, last.y);
    }
    if (evenCol) {
      return new Point2D.Double(last.x, last.y + 1);
    }
    return new Point2D.Double(last.x, last.y - 1);
  }
}
