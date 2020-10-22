package com.gradybward.pointlist;

import java.awt.geom.Point2D;
import java.util.List;

public interface PointListGenerator {
  public List<Point2D.Double> generate(int nPoints);
}
