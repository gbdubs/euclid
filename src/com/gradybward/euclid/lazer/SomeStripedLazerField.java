package com.gradybward.euclid.lazer;

import static com.gradybward.euclid.PointUtils.pointFromLinePlusDistance;

import java.awt.geom.Point2D;

import com.gradybward.euclid.Construction;

public final class SomeStripedLazerField {

  private final LazerField lazerField;
  private final boolean[] renderStripes;

  public SomeStripedLazerField(LazerField lazerField, boolean... stripes) {
    this.lazerField = lazerField;
    this.renderStripes = stripes;
  }

  public void addToConstruction(Construction c) {
    double[] acceptableARange = Utils.getARange(this.lazerField.getPoints());
    Point2D.Double p1 = lazerField.getPoints().get(0);
    Point2D.Double p2 = lazerField.getPoints().get(1);
    Point2D.Double aMin = pointFromLinePlusDistance(p1, p2, acceptableARange[0]);
    Point2D.Double aMax = pointFromLinePlusDistance(p1, p2, acceptableARange[1]);
    double d = aMin.distance(aMax);
    for (int i = 0; i < renderStripes.length; i++) {
      if (!renderStripes[i]) {
        continue;
      }
      double nd = d * (i + 1) / (renderStripes.length + 1);
      Point2D.Double a = pointFromLinePlusDistance(aMin, aMax, nd);
      lazerField.addDebuggingConstructionHintsForPathThroughField(c, a);
      c.resultingIn(lazerField.getPathThroughField(a));
    }
  }
}
