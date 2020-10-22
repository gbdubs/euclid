package com.gradybward.euclid.lazer;

import java.awt.geom.Point2D;
import java.util.List;

import com.gradybward.euclid.Construction;

public interface LazerField {
  public void addToConstructionPathStartingFromPoint(Construction construction, Point2D.Double p);

  public List<Point2D.Double> getPoints();
}
