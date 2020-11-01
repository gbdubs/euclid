package com.gradybward.euclid.filling;

import java.awt.geom.Area;

import com.gradybward.euclid.elements.Path;

public class FillingFromClosedPaths implements Filling {

  @Override
  public Area enclosedByPaths(Path path1, Path path2) {
    Area area = new Area(path1.toPath2D());
    area.exclusiveOr(new Area(path2.toPath2D()));
    return area;
  }
}
