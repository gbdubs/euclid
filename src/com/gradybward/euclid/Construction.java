package com.gradybward.euclid;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gradybward.euclid.elements.Element;

/** A record of a proof as it unfolds. Used for debug and display. */
public class Construction {
  private List<Element> suppositions;
  private List<Element> constructions;
  private List<Element> theorems;

  public Construction() {
    this.suppositions = new ArrayList<>();
    this.constructions = new ArrayList<>();
    this.theorems = new ArrayList<>();
  }

  public Construction suppose(Element... elements) {
    this.suppositions.addAll(Arrays.asList(elements));
    return this;
  }

  public Construction suppose(Point2D.Double... points) {
    for (Point2D.Double p : points) {
      suppositions.add(Element.point(p));
    }
    return this;
  }

  public Construction thenConstruct(Element... elements) {
    this.constructions.addAll(Arrays.asList(elements));
    return this;
  }

  public Construction resultingIn(Element... elements) {
    this.theorems.addAll(Arrays.asList(elements));
    return this;
  }

  public void done(String filename) {
    SVGPrinter result = new SVGPrinter();
    result.setColor(Color.BLACK);
    result.drawAll(theorems);
    result.print(filename);
  }

  public void doneDebug(String filename) {
    SVGPrinter result = new SVGPrinter();
    result.setColor(new Color(225, 225, 225));
    result.drawAll(constructions);
    result.setColor(Color.RED);
    result.drawAll(suppositions);
    result.setColor(Color.BLACK);
    result.drawAll(theorems);
    result.print(filename);
  }
}
