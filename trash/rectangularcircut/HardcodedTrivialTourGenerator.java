package com.gradybward.pointlist.rectangularcircut;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HardcodedTrivialTourGenerator extends TourGenerator {

  public HardcodedTrivialTourGenerator(int w, int h, Random random) {
    super(w, h, random);
  }

  @Override
  protected List<Rectangle> segment() {
    int w1 = width / 2;
    int w2 = width - w1;
    int h1 = height / 2;
    int h2 = height - h1;
    return Arrays.asList(
        new Rectangle(new Point2D.Double(0, 0), w1 , h1),
        new Rectangle(new Point2D.Double(w1, 0), w2, h1),
        new Rectangle(new Point2D.Double(w1, h1), w2, h2),
        new Rectangle(new Point2D.Double(0, h1), w1, h2));
  }
}
