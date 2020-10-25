package com.gradybward.pointlist;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.gradybward.euclid.lazer.Utils;

public class Constraints {
  public static Constraint noPointsAreCloserThan(double minDistance) {
    return (point, others) -> {
      return others.stream().mapToDouble(o -> o.distance(point)).min()
          .orElse(Double.MAX_VALUE) > minDistance;
    };
  }

  public static Constraint noNonAdjacentPointsAreCloserThan(double minDistance) {
    return (point, others) -> {
      Point2D.Double last = others.get(others.size() - 1);
      return others.stream().filter(o -> !o.equals(last)).mapToDouble(o -> o.distance(point)).min()
          .orElse(Double.MAX_VALUE) > minDistance;
    };
  }

  public static Constraint aRangeIsAtLeast(double threshold) {
    return (point, others) -> {
      List<Point2D.Double> modified = new ArrayList<>(others);
      modified.add(point);
      double[] allowableAs = Utils.getARange(modified);
      return allowableAs[1] - allowableAs[0] > threshold;
    };
  }

  public static Constraint noDuplicates() {
    return noPointsAreCloserThan(.01);
  }
  
  public static Constraint allFinite() {
    return (point, others) -> {
      return isFinite(point.x) && isFinite(point.y);
    };
  }
  
  private static boolean isFinite(Double d) {
    return Double.isFinite(d) && d < Double.MAX_VALUE && d > Double.MIN_VALUE;
  }

  public static Constraint preferNonDuplicates() {
    return (point, others) -> {
      return Math.random() < .1 || noPointsAreCloserThan(.01).isAcceptableNextPoint(point, others);
    };
  }
}
