package com.gradybward.euclid.lazer;

import static com.gradybward.euclid.PointUtils.pointFromLinePlusDistance;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;

import com.gradybward.euclid.Construction;
import com.gradybward.euclid.Element;

public class ContiguousLazerField implements LazerField {
  private final List<Point2D.Double> points;

  public ContiguousLazerField(List<Point2D.Double> points) {
    this.points = new ArrayList<>(points);
  }

  @Override
  public void addToConstructionPathStartingFromPoint(Construction c, Point2D.Double lastArcPoint) {
    c.suppose(points.toArray(new Point2D.Double[0]));
    Point2D.Double lastCenter = points.get(0);
    boolean directionCW = true;
    for (int i = 1; i < points.size() - 1; i++) {
      Point2D.Double center = points.get(i);
      Point2D.Double nextCenter = points.get(i + 1);
      Point2D.Double arcPoint = pointFromLinePlusDistance(center, nextCenter,
          center.distance(lastArcPoint));
      c.thenConstruct(Element.segment(center, nextCenter));
      c.thenConstruct(Element.circle(center, arcPoint));

      if (resultShouldToggleDirectionFromLast(lastArcPoint, lastCenter, center)) {
        directionCW = !directionCW;
      }
      if (directionCW) {
        c.resultingIn(Element.arcFromAroundToCW(lastArcPoint, center, arcPoint));
      } else {
        c.resultingIn(Element.arcFromAroundToCCW(lastArcPoint, center, arcPoint));
      }
      lastArcPoint = arcPoint;
      lastCenter = center;
    }
  }

  @Override
  public List<Point2D.Double> getPoints() {
    return points;
  }

  private static boolean resultShouldToggleDirectionFromLast(Double lastArcPoint, Double lastCenter,
      Double nextCenter) {
    return lastCenter.distance(lastArcPoint) < lastCenter.distance(nextCenter);
  }
}
