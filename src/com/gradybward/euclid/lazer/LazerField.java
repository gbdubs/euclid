package com.gradybward.euclid.lazer;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;

import com.gradybward.euclid.Construction;
import com.gradybward.euclid.elements.Path;

public interface LazerField { 
  public List<Point2D.Double> getPoints();

  public Path getPathThroughField(Point2D.Double a);

  void addDebuggingConstructionHintsForPathThroughField(Construction c, Double a);
}
