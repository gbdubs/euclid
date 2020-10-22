package com.gradybward.euclid.lazer;

import static com.gradybward.euclid.PointUtils.pointFromLinePlusDistance;

import java.awt.geom.Point2D;

import com.gradybward.euclid.Construction;

public final class StripedLazerField {

  private final LazerField lazerField;
  private final int nStripes;

  public StripedLazerField(int nStripes, LazerField lazerField) {
    this.nStripes = nStripes;
    this.lazerField = lazerField;
  }

  public void addToConstruction(Construction c) {
    Point2D.Double p1 = lazerField.getPoints().get(0);
    Point2D.Double p2 = lazerField.getPoints().get(1);
    double d = p1.distance(p2);
    for (int i = 0; i < nStripes; i++) {
      double nd = d * (i + 1) / (nStripes + 1);
      lazerField.addToConstructionPathStartingFromPoint(c, pointFromLinePlusDistance(p1, p2, nd));
    }
  }
}
