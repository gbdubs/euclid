package com.gradybward.pointlist.rectangularcircut;

import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TourGeneratorTest {

  private final TourGenerator tourGenerator;
  private final int width;
  private final int height;
  
  public TourGeneratorTest(String tourGeneratorType, Integer randomSeed, TourGenerator tourGenerator, int width, int height) {
    this.tourGenerator = tourGenerator;
    this.width = width;
    this.height = height;
  }

  @Test
  public void tourCanBeCreated() {
    tourGenerator.getTour();
  }

  @Test
  public void tourHasNoDuplicates() {
    List<Point2D.Double> points = tourGenerator.getTour();
    for (int i = 0; i < points.size(); i++) {
      for (int j = 0; j < points.size(); j++) {
        if (i == j) {
          continue;
        }
        Point2D.Double p1 = points.get(i);
        Point2D.Double p2 = points.get(j);
        assertTrue(String.format("Expected points to be non duplicates, but they were not at index i=%s j=%s: %s, %s", i, j, p1, p2), p1.distance(p2) > .99);
      }
    }
  }
  
  @Test
  public void tourIsComplete() {
    List<Point2D.Double> points = tourGenerator.getTour();
    assertTrue(String.format("Point Set is not complete, expected %s elements but had %s.\n%s", height * width, points.size(), points.toString()), points.size() == width * height);
  }
  
  @Test
  public void tourIsAdjacent() {
    List<Point2D.Double> points = tourGenerator.getTour();
    for (int i = 0; i < points.size(); i++) {
      Point2D.Double p1 = points.get(i);
      Point2D.Double p2 = points.get((i + 1) % points.size());
      assertTrue(String.format("Expected points to be adjacent, but they were not at index i=%s: %s, %s", i, p1, p2), p1.distance(p2) < 1.01);
    }
  }
  
  private static interface TourGeneratorFunction {
    TourGenerator apply(int width, int height, int randomSeed);
  }

  @Parameterized.Parameters(name = "{0} ({3}x{4}) - #{1} ")
  public static List<Object[]> testCases() {
    List<TourGeneratorFunction> generators = Arrays.asList(
        (w, h, r) -> new HardcodedTrivialTourGenerator(w, h, new Random(r)));
    int minW = 6;
    int maxW = 10;
    int minH = 6;
    int maxH = 10;
    int minRandomSeed = 0;
    int maxRandomSeed = 4;
    
    List<Object[]> testCases = new ArrayList<>();
   
    for (TourGeneratorFunction generator : generators) {
        for (int w = minW; w <= maxW; w++) {
          for (int h = minH; h <= maxH; h++) {
            for (int randomSeed = minRandomSeed; randomSeed <= maxRandomSeed; randomSeed++) {
              TourGenerator tg = generator.apply(w, h, randomSeed);
              testCases.add(new Object[] {tg.getClass().getSimpleName(), randomSeed, tg, w, h});
            }
          }
        }
      }
    return testCases;
  }
}
