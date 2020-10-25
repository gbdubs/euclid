package com.gradybward.euclid.lazer;

import java.awt.geom.Point2D;
import java.util.List;

public class Utils {

  public static double[] getARange(List<Point2D.Double> points) {
    double minAllowed = 0;
    double maxAllowed = Double.MAX_VALUE;
    double runningDelta = 0;
    for (int i = 0; i < points.size() - 1; i++) {
      double dist = points.get(i).distance(points.get(i + 1));
      if (i % 2 == 0) {
        runningDelta += dist;
        maxAllowed = Math.min(maxAllowed, runningDelta);
      } else {
        runningDelta -= dist;
        minAllowed = Math.max(minAllowed, runningDelta);
      }
    }
    return new double[] { minAllowed, maxAllowed };
  }

  /*
   * 
   * public static void main(String[] args) { System.out.println(test(10, 10, 10) +
   * " Expected = [0, 10]"); System.out.println(test(1, 2, 11) + " Expected = [0, 1]");
   * System.out.println(test(4, 2, 1) + " Expected = [2, 3]"); }
   * 
   * private static String test(int... lengths) { List<Point2D.Double> points = new ArrayList<>();
   * Point2D.Double last = new Point2D.Double(); points.add(last); for (int length : lengths) { last
   * = new Point2D.Double(last.x + length, 0); points.add(last); } double[] arange =
   * getARange(points); return String.format("Input: %s, MIN = %s, MAX = %s\n",
   * Arrays.toString(lengths), arange[0], arange[1]); }
   */
}
