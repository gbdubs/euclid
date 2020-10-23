package com.gradybward.euclid.elements;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.apache.batik.svggen.SVGGraphics2D;

class Rendering {

  public static void drawPoint(SVGGraphics2D graphics, Point2D.Double p) {
    int radius = 3;
    Color initialColor = graphics.getColor();
    graphics.setColor(Color.WHITE);
    graphics.fillArc((int) p.x - radius, (int) p.y - radius, radius * 2, radius * 2, 0, 360);
    graphics.setColor(initialColor);
    graphics.drawArc((int) p.x - radius, (int) p.y - radius, radius * 2, radius * 2, 0, 360);
  }

  private Rendering() {}
}
