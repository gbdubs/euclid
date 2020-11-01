package com.gradybward.pointlist.rectangularcircut;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

import com.gradybward.gridtraversals.rectangles.Corner;
import com.gradybward.gridtraversals.rectangles.Providers;
import com.gradybward.gridtraversals.rectangles.RectangularGridTraversal;
import com.gradybward.gridtraversals.rectangles.RectangularGridTraversalProvider;
import com.gradybward.hamiltonian.HamiltonianCycleSolver;

abstract class TourGenerator {

  protected final int width;
  protected final int height;
  protected final Random random;

  protected TourGenerator(int w, int h, Random random) {
    this.width = w;
    this.height = h;
    this.random = random;
  }

  protected abstract List<Rectangle> segment();  

  public List<Point2D.Double> getTour() {
    List<Rectangle> rectangles = generateAfterNAttemptsOrFail(10, () -> attemptToGetRectangleTour());
    List<Point2D.Double> result = new ArrayList<>();
    for (Rectangle rectangle : rectangles) {
      Corner from = getCornerFromCoords(rectangle.topLeft, rectangle.in);
      Corner to = getCornerFromCoords(rectangle.topLeft, rectangle.out);
      List<RectangularGridTraversalProvider> providers = 
          Providers.getProvidersForSituation(rectangle.w, rectangle.h, from, to); 
      RectangularGridTraversalProvider provider = providers.get((int) Math.floor(providers.size() * random.nextDouble()));
      RectangularGridTraversal grid = provider.provideStartingAtPoint(
          rectangle.in, 
          rectangle.w,
          rectangle.h, 
          from, 
          to);
      rectangle.validateGeneratedGrid(grid);
      result.addAll(grid.get());
    }
    return result;
  }
  

  private Optional<List<Rectangle>> attemptToGetRectangleTour() {
    List<Rectangle> rectangles = segment();
    Optional<List<Rectangle>> maybeCycle = HamiltonianCycleSolver.BFS()
        .findHamiltonianCycle(rectangles, (r1, r2) -> r1.hasCornerAdjacentToCornerInOtherRectangle(r2));
    if (!maybeCycle.isPresent()) {
      return Optional.empty();
    }
    System.out.println("MAYBE CYCLE");
    List<Rectangle> cycle = maybeCycle.get();
    for (int i = 0; i < cycle.size(); i++) {
      Rectangle a = cycle.get(i);
      Rectangle b = cycle.get((i + 1) % cycle.size());
      List<Point2D.Double> asCorners = a.getCornersAdjacentToOtherRectanglesCorners(b);
      asCorners.remove(a.in);
      if (b.out != null && a.getAdjacentCorner(b.out).isPresent()) {
        asCorners.remove(a.getAdjacentCorner(b.out).get());
      }
      if (asCorners.isEmpty()) {
        return Optional.empty();
      }
      Point2D.Double ac = asCorners.get((int) Math.floor(random.nextDouble() * asCorners.size()));
      System.out.println(asCorners.toString());
      a.out = ac;
      b.in = b.getAdjacentCorner(ac).get();
    }
    for (Rectangle rectangle : cycle) {
      if (Providers.getProvidersForSituation(rectangle.w, rectangle.h, getCornerFromCoords(rectangle.topLeft, rectangle.in), getCornerFromCoords(rectangle.topLeft, rectangle.out)).isEmpty()) {
        return Optional.empty();
      }
    }
    return Optional.of(cycle);
  }

  private Corner getCornerFromCoords(Double topLeft, Double in) {
    if (in.x - topLeft.x > .01) {
      if (in.y - topLeft.y > .01) {
        return Corner.BOTTOM_RIGHT;
      }
      return Corner.TOP_RIGHT;
    }
    if (in.y - topLeft.y > .01) {
      return Corner.BOTTOM_LEFT;
    }
    return Corner.TOP_LEFT;
  }
  
  private static <T> T generateAfterNAttemptsOrFail(int attempts, Supplier<Optional<T>> supplier) {
    while (attempts-- > 0) {
      Optional<T> result = supplier.get();
      if (result.isPresent()) {
        return result.get();
      }
    }
    throw new RuntimeException("Failed to find a suitable option.");
  }
}
