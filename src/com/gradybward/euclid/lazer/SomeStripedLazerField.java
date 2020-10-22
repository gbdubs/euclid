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
    Point2D.Double p1 = lazerField.getPoints().get(0);
    Point2D.Double p2 = lazerField.getPoints().get(1);
    double d = p1.distance(p2);
    for (int i = 0; i < renderStripes.length; i++) {
      if (!renderStripes[i]) {
        continue;
      }
      double nd = d * (i + 1) / (renderStripes.length + 1);
      lazerField.addToConstructionPathStartingFromPoint(c, pointFromLinePlusDistance(p1, p2, nd));
    }
  }
}
