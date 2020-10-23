package com.gradybward.pointlist;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PointListGenerator {

  private final Point2D.Double start;
  private final PointGenerator pointGenerator;
  private final List<Constraint> constraints;

  public PointListGenerator(Point2D.Double start, PointGenerator pg, Constraint... constraints) {
    this.start = start;
    this.pointGenerator = pg;
    this.constraints = Arrays.asList(constraints);
  }

  private boolean isAcceptableNextPoint(Point2D.Double point, List<Point2D.Double> others) {
    return constraints.stream().allMatch(c -> c.isAcceptableNextPoint(point, others));
  }

  public List<Point2D.Double> generate(int nPoints) {
    List<Point2D.Double> points = new ArrayList<>();
    points.add(start);
    for (int i = 0; i < nPoints - 1; i++) {
      Point2D.Double last = points.get(points.size() - 1);
      Point2D.Double p;
      int attempts = 0;
      do {
        p = pointGenerator.generate(last);
        attempts++;
        if (attempts > 100) {
          throw new RuntimeException("Help! I've gotten in a loop and I can't get out!");
        }
      } while (!isAcceptableNextPoint(p, points));
      points.add(p);
    }
    return points;
  }
}
