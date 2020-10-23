package com.gradybward.euclid.lazer;

import static com.gradybward.euclid.PointUtils.pointFromLinePlusDistance;

import java.awt.geom.Point2D;

import com.gradybward.euclid.Construction;

public final class FunkyStripedLazerField {

  private final LazerField lazerField;
  private final int nStripes;

  public FunkyStripedLazerField(int nStripes, LazerField lazerField) {
    this.nStripes = nStripes;
    this.lazerField = lazerField;
  }

  public void addToConstruction(Construction c) {
    double[] acceptableARange = Utils.getARange(this.lazerField.getPoints());
    Point2D.Double p1 = lazerField.getPoints().get(0);
    Point2D.Double p2 = lazerField.getPoints().get(1);
    Point2D.Double aMin = pointFromLinePlusDistance(p1, p2, acceptableARange[0]);
    Point2D.Double aMax = pointFromLinePlusDistance(p1, p2, acceptableARange[1]);
    double d = aMin.distance(aMax);
    for (int i = 0; i < nStripes; i++) {
      double nd = d * i / (nStripes - 1);
      lazerField.addToConstructionPathStartingFromPoint(c,
          pointFromLinePlusDistance(aMin, aMax, nd));
    }
  }
}
