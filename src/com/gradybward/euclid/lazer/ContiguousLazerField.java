package com.gradybward.euclid.lazer;

import static com.gradybward.euclid.PointUtils.pointFromLinePlusDistance;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.gradybward.euclid.Construction;
import com.gradybward.euclid.elements.Element;
import com.gradybward.euclid.elements.Path;
import com.gradybward.euclid.elements.PathElement;

public class ContiguousLazerField implements LazerField {
  private final List<Point2D.Double> points;

  public ContiguousLazerField(List<Point2D.Double> points) {
    this.points = new ArrayList<>(points);
  }
 
  @Override
  public Path getPathThroughField(Point2D.Double lastArcPoint) {
    List<PathElement> path = new ArrayList<>();
    Point2D.Double lastCenter = points.get(0);
    boolean directionCW = true;
    for (int i = 1; i < points.size() - 1; i++) {
      Point2D.Double center = points.get(i);
      Point2D.Double nextCenter = points.get(i + 1);
      Point2D.Double arcPoint = pointFromLinePlusDistance(center, nextCenter,
          center.distance(lastArcPoint));
      if (resultShouldToggleDirectionFromLast(arcPoint, lastArcPoint, lastCenter, center)) {
        directionCW = !directionCW;
      }
      if (directionCW) {
        path.add(Element.arcFromAroundToCW(lastArcPoint, center, arcPoint));
      } else {
        path.add(Element.arcFromAroundToCCW(lastArcPoint, center, arcPoint));
      }
      lastArcPoint = arcPoint;
      lastCenter = center;
    }
    return Element.path(path);
  }
  
  @Override
  public void addDebuggingConstructionHintsForPathThroughField(Construction c, Point2D.Double lastArcPoint) {
    c.suppose(points.toArray(new Point2D.Double[0]));
    for (int i = 1; i < points.size() - 1; i++) {
      Point2D.Double center = points.get(i);
      Point2D.Double nextCenter = points.get(i + 1);
      Point2D.Double arcPoint = pointFromLinePlusDistance(center, nextCenter,
          center.distance(lastArcPoint));
      c.thenConstruct(Element.segment(center, nextCenter));
      c.thenConstruct(Element.circle(center, arcPoint));
      lastArcPoint = arcPoint;
    }
  }

  @Override
  public List<Point2D.Double> getPoints() {
    return points;
  }

  private static boolean resultShouldToggleDirectionFromLast(Point2D.Double arcPoint,
      Point2D.Double lastArcPoint, Point2D.Double lastCenter, Point2D.Double nextCenter) {
    return arcPoint.equals(lastArcPoint)
        || lastCenter.distance(lastArcPoint) < lastCenter.distance(nextCenter);
  }
}
