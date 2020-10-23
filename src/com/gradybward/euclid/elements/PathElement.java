package com.gradybward.euclid.elements;

import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

public interface PathElement extends Element {

  Point2D.Double getStart();

  Point2D.Double getEnd();

  PathElement reverse();

  PathIterator getPathIterator();
}
