package com.gradybward.euclid.filling;

import java.awt.geom.Area;

import com.gradybward.euclid.elements.Path;

public interface Filling {
  Area enclosedByPaths(Path path1, Path path2);
}
