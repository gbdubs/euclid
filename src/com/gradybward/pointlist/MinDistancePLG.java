package com.gradybward.pointlist;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public abstract class MinDistancePLG implements PointListGenerator {

  private final Point2D.Double start;
  private final double minDistance;
  private final List<Point2D.Double> toAvoid;

  public MinDistancePLG(Point2D.Double start, double minDistance) {
    this.start = start;
    this.minDistance = minDistance;
    this.toAvoid = new ArrayList<>();
  }

  public MinDistancePLG(Point2D.Double start, Double minDistance, List<Point2D.Double> toAvoid) {
    this.start = start;
    this.minDistance = minDistance;
    this.toAvoid = new ArrayList<>(toAvoid);
  }

  public abstract Point2D.Double generate(Point2D.Double last);

  @Override
  public List<Point2D.Double> generate(int nPoints) {
    List<Point2D.Double> points = new ArrayList<>();
    points.add(start);
    for (int i = 0; i < nPoints - 1; i++) {
      Point2D.Double last = points.get(points.size() - 1);
      Point2D.Double p;
      int attempts = 0;
      do {
        p = generate(last);
        attempts++;
        if (attempts > 100) {
          throw new RuntimeException("Help! I've gotten in a loop and I can't get out!");
        }
      } while (proposedIsInContentionWithAnother(p, points));
      points.add(p);
      toAvoid.add(p);
    }
    return points;
  }

  private boolean proposedIsInContentionWithAnother(Point2D.Double p, List<Point2D.Double> points) {
    return toAvoid.stream().mapToDouble(o -> o.distance(p)).min()
        .orElse(Double.MAX_VALUE) < minDistance;
  }
}
