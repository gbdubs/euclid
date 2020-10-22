package com.gradybward.euclid.lazer;

import static com.gradybward.euclid.PointUtils.pointFromLinePlusDistance;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import com.gradybward.euclid.Construction;
import com.gradybward.euclid.Element;

public final class AlternatingLazerField implements LazerField {
  private final List<Point2D.Double> points;

  public AlternatingLazerField(Point2D.Double... pts) {
    this.points = Arrays.asList(pts);
  }

  @Override
  public void addToConstructionPathStartingFromPoint(Construction c, Point2D.Double a) {
    c.suppose(points.toArray(new Point2D.Double[0]));
    for (int i = 0; i < points.size() - 1; i++) {
      Point2D.Double p = points.get(i);
      Point2D.Double an = pointFromLinePlusDistance(p, points.get(i + 1), p.distance(a));
      c.thenConstruct(Element.circle(p, an));
      c.thenConstruct(Element.point(an));
      if (i % 2 == 0) {
        c.resultingIn(Element.arcFromAroundToCW(a, p, an));
      } else {
        c.resultingIn(Element.arcFromAroundToCCW(a, p, an));
      }
      a = an;
    }
  }

  @Override
  public List<Point2D.Double> getPoints() {
    return points;
  }
}
