package com.gradybward.pointlist;

import java.awt.geom.Point2D;
import java.util.List;

public interface Constraint {
  public boolean isAcceptableNextPoint(Point2D.Double point, List<Point2D.Double> others);
}
