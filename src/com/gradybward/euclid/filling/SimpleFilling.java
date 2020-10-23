package com.gradybward.euclid.filling;

import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import com.gradybward.euclid.elements.Path;

public class SimpleFilling implements Filling {
  @Override
  public Area enclosedByPaths(Path a, Path b) {
    Point2D.Double aStart = a.getStart();
    if (a.getStart().distance(b.getStart()) < a.getStart().distance(b.getEnd())) {
      b = b.reverse();
    }
    Point2D.Double bStart = b.getStart();
    Path2D.Double p = a.toPath2D();
    p.lineTo(bStart.x, bStart.y);
    p.append(b.toPath2D(), true);
    p.lineTo(aStart.x, aStart.y);
    p.closePath();
    return new Area(p);
  }
}
