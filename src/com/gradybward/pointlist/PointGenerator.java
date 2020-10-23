package com.gradybward.pointlist;

import java.awt.geom.Point2D;

public interface PointGenerator {
  public Point2D.Double generate(Point2D.Double last);
}
