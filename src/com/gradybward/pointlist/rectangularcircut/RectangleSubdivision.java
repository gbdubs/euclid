package com.gradybward.pointlist.rectangularcircut;

import java.util.List;
import java.util.Optional;

import com.gradybward.hamiltonian.HamiltonianCycleSolver;

public abstract class RectangleSubdivision {

  protected final int height;
  protected final int width;
  
  public RectangleSubdivision(int h, int w) {
    this.height = h;
    this.width = w;
  }
  
  protected abstract List<Rectangle> segment();
  
  public List<Rectangle> generateRectangleTour() {
    int attempts = 0;
    while (attempts++ < 10) {
      List<Rectangle> rectangles = segment();
      Optional<List<Rectangle>> cycle = HamiltonianCycleSolver.BFS().findHamiltonianCycle(rectangles, (r1, r2) -> r1.hasCommonCorner(r2));
      if (!cycle.isPresent()) {
        continue;
      }
      for (int i = 0; i < cycle.get().size(); i++) {
        Rectangle a = cycle.get().get(i);
        Rectangle b = cycle.get().get((i + 1) % cycle.get().size());
        List<Point> ccs = a.getCommonCorners(b);
        Point cc = ccs.get((int) Math.floor(Math.random() * ccs.size()));
        a.out = cc;
        b.in = cc;
      }
      return cycle.get();
    }
    throw new RuntimeException("Didn't find any tours!");
  }
}
