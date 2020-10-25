import java.awt.Color;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gradybward.colors.coolors.CoolorsColorPalletes;
import com.gradybward.euclid.Construction;
import com.gradybward.euclid.PointUtils;
import com.gradybward.euclid.SVGPrinter;
import com.gradybward.euclid.elements.Path;
import com.gradybward.euclid.filling.SimpleFilling;
import com.gradybward.euclid.lazer.ContiguousLazerField;
import com.gradybward.euclid.lazer.FunkyStripedLazerField;
import com.gradybward.euclid.lazer.LazerField;
import com.gradybward.euclid.lazer.Utils;
import com.gradybward.pointlist.Constraints;
import com.gradybward.pointlist.PointListGenerator;
import com.gradybward.pointlist.RandomRadiusPointGenerator;
import com.gradybward.pointlist.RectangularGridRandomPointGenerator;
import com.gradybward.pointlist.RectangularGridSnakePointGenerator;
import com.gradybward.pointlist.TriangularGridRandomPointGenerator;

public class Main {

  public static void main(String[] args) {
    overshooting();
  }
  
  private static void overshooting(){
    SVGPrinter printer = new SVGPrinter();
    com.gradybward.colors.Color[] colors = CoolorsColorPalletes.getRandomPallete().get();
    List<Point2D.Double> points = new PointListGenerator(
        new Point2D.Double(), 
        new TriangularGridRandomPointGenerator(1),
        Constraints.preferNonDuplicates(),
        Constraints.aRangeIsAtLeast(.5)).generate(20);
    LazerField lf = new ContiguousLazerField(points);
    double[] aRange = Utils.getARange(points);
    double aRangeStartProp = .5;
    double aRangeEndProp = .75;

    Path p1 = lf.getPathThroughField(PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), ((aRange[1] - aRange[0]) * aRangeStartProp) + aRange[0]));
    Path p2 = lf.getPathThroughField(PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), ((aRange[1] - aRange[0]) * aRangeEndProp) + aRange[0]));
    Area a = new SimpleFilling().enclosedByPaths(p1, p2);
    printer.setColor(colors[0].get());
    printer.fill(a);
    
    printer.print("output/Overshooting");
  }
  
  private static void consensus2(){
    SVGPrinter printer = new SVGPrinter();
    int numSnakes = 80;
    int snakeMinLength = 4;
    int numSegments = 6;
    int minPosition = 1;
    int maxPosition = 2;
    Color[] colors = new Color[] {new Color(78,20,140), new Color(97,61,193), new Color(139,128,249)};
    for (int i = 0; i < numSnakes; i++) {
      int snakeLength = snakeMinLength + (int) Math.round(Math.random() * 3);
      List<Point2D.Double> points = new PointListGenerator(
          new Point2D.Double(), 
          new TriangularGridRandomPointGenerator(1),
          Constraints.preferNonDuplicates(),
          Constraints.aRangeIsAtLeast(.5)).generate(snakeLength);
      LazerField lf = new ContiguousLazerField(points);
      double[] bounds = Utils.getARange(points);
      double startProp = ((double) (i % (maxPosition - minPosition)) + minPosition) / (numSegments);
      double endProp = startProp + 1.0 / numSegments;
      double d1 = ((bounds[1] - bounds[0]) * startProp) + bounds[0];
      double d2 = ((bounds[1] - bounds[0]) * endProp) + bounds[0];
      Point2D.Double a1 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d1);
      Point2D.Double a2 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d2);
      Path p1 = lf.getPathThroughField(a1);
      Path p2 = lf.getPathThroughField(a2);
      Area a = new SimpleFilling().enclosedByPaths(p1, p2);
      printer.setColor(getShadeOfTransparent(colors[i%colors.length], i * 1.0 / numSnakes));
      printer.fill(a);
    }
    printer.print("Consensus2");
  }
  
  private static void consensus(){
    SVGPrinter printer = new SVGPrinter();
    int numSnakes = 80;
    int snakeLength = 7;
    int numSegments = 6;
    int minPosition = 1;
    int maxPosition = 2;
    Color[] colors = new Color[] {new Color(78,20,140), new Color(97,61,193), new Color(139,128,249)};
    for (int i = 0; i < numSnakes; i++) {
      List<Point2D.Double> points = new PointListGenerator(
          new Point2D.Double(), 
          new TriangularGridRandomPointGenerator(1),
          Constraints.preferNonDuplicates(),
          Constraints.aRangeIsAtLeast(.5)).generate(snakeLength);
      LazerField lf = new ContiguousLazerField(points);
      double[] bounds = Utils.getARange(points);
      double startProp = ((double) (i % (maxPosition - minPosition)) + minPosition) / (numSegments);
      double endProp = startProp + 1.0 / numSegments;
      double d1 = ((bounds[1] - bounds[0]) * startProp) + bounds[0];
      double d2 = ((bounds[1] - bounds[0]) * endProp) + bounds[0];
      Point2D.Double a1 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d1);
      Point2D.Double a2 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d2);
      Path p1 = lf.getPathThroughField(a1);
      Path p2 = lf.getPathThroughField(a2);
      Area a = new SimpleFilling().enclosedByPaths(p1, p2);
      printer.setColor(getShadeOfTransparent(colors[i%colors.length], i * 1.0 / numSnakes));
      printer.fill(a);
    }
    printer.print("Consensus");
  }
  
  private static void plaidGradient() {
    SVGPrinter printer = new SVGPrinter();
    int gridSize = 5;
    int gridCount = 25;
    int numSegments = 10;
    int numSnakes = 2 * numSegments;
    for (int i = 0; i < numSnakes; i++) {
      List<Point2D.Double> points = new PointListGenerator(
          new Point2D.Double(), 
          new RectangularGridSnakePointGenerator(gridSize, gridSize, i % 2 == 0)).generate(gridCount);
      LazerField lf = new ContiguousLazerField(points);
      double[] bounds = Utils.getARange(points);
      double startProp = (i % numSegments) * 1.0 / numSegments;
      double endProp = startProp + 1.0 / numSegments;
      double d1 = ((bounds[1] - bounds[0]) * startProp) + bounds[0];
      double d2 = ((bounds[1] - bounds[0]) * endProp) + bounds[0];
      Point2D.Double a1 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d1);
      Point2D.Double a2 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d2);
      Path p1 = lf.getPathThroughField(a1);
      Path p2 = lf.getPathThroughField(a2);
      Area a = new SimpleFilling().enclosedByPaths(p1, p2);
      double opacity = .8 * ((i % numSegments) + 1.0) / numSegments;
      printer.setColor(getShadeOfTransparent(i % 2 == 0 ? Color.BLACK :Color.BLACK, opacity));
      printer.fill(a);
    }
    printer.print("GradientPlaid");
  }
    
  public static void plaid3() {
    SVGPrinter printer = new SVGPrinter();
    int gridSize = 7;
    int numSegments = 8;
    int numSnakes = 8;
    int[] propAssignment = new int[] {0, 7, 6, 1, 2, 5, 4, 3};
    boolean[] horizAssignment = new boolean[] {true, false, true, false, true, false, true, false};
    for (int i = 0; i < numSnakes; i++) {
      List<Point2D.Double> points = new PointListGenerator(
          new Point2D.Double(), 
          new RectangularGridSnakePointGenerator(gridSize, gridSize, horizAssignment[i])).generate(gridSize * gridSize);
      LazerField lf = new ContiguousLazerField(points);
      double[] bounds = Utils.getARange(points);
      double startProp = propAssignment[i] * 1.0 / numSegments;
      double endProp = startProp + 1.0 / numSegments;
      double d1 = ((bounds[1] - bounds[0]) * startProp) + bounds[0];
      double d2 = ((bounds[1] - bounds[0]) * endProp) + bounds[0];
      Point2D.Double a1 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d1);
      Point2D.Double a2 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d2);
      Path p1 = lf.getPathThroughField(a1);
      Path p2 = lf.getPathThroughField(a2);
      Area a = new SimpleFilling().enclosedByPaths(p1, p2);
      double opacity = .75 - Math.abs(3.5 - propAssignment[i]) / 7;
      System.out.println("propAssignment = " + propAssignment[i] + " opacity = " + opacity);
      printer.setColor(getShadeOfTransparent(i % 2 == 0 ? Color.RED :Color.BLUE, opacity));
      printer.fill(a);
    }
    printer.print("Plaid3");
  }
    
  public static void plaid2() {
    SVGPrinter printer = new SVGPrinter();
    int gridSize = 7;
    int numSegments = 9;
    int numSnakes = 8;
    int[] propAssignment = new int[] {1, 2, 8, 7, 3, 4, 5, 6};
    boolean[] horizAssignment = new boolean[] {true, false, true, false, true, false, true, false};
    for (int i = 0; i < numSnakes; i++) {
      List<Point2D.Double> points = new PointListGenerator(
          new Point2D.Double(), 
          new RectangularGridSnakePointGenerator(gridSize, gridSize, horizAssignment[i])).generate(gridSize * gridSize);
      LazerField lf = new ContiguousLazerField(points);
      double[] bounds = Utils.getARange(points);
      double startProp = propAssignment[i] * 1.0 / numSegments;
      double endProp = startProp + 1.0 / numSegments;
      double d1 = ((bounds[1] - bounds[0]) * startProp) + bounds[0];
      double d2 = ((bounds[1] - bounds[0]) * endProp) + bounds[0];
      Point2D.Double a1 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d1);
      Point2D.Double a2 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d2);
      Path p1 = lf.getPathThroughField(a1);
      Path p2 = lf.getPathThroughField(a2);
      Area a = new SimpleFilling().enclosedByPaths(p1, p2);
      double opacity = .75 - Math.abs(4.5 - propAssignment[i]) / 7;
      printer.setColor(getShadeOfTransparent(i % 2 == 0 ? Color.RED :Color.BLUE, opacity));
      printer.fill(a);
    }
    printer.print("Plaid2");
  }
  
  public static void plaid() {
    SVGPrinter printer = new SVGPrinter();
    int gridSize = 7;
    int numSegments = 9;
    int numSnakes = 8;
    int[] propAssignment = new int[] {1, 1, 7, 7, 3, 3, 5, 5};
    boolean[] horizAssignment = new boolean[] {true, false, true, false, true, false, true, false};
    for (int i = 0; i < numSnakes; i++) {
      List<Point2D.Double> points = new PointListGenerator(
          new Point2D.Double(), 
          new RectangularGridSnakePointGenerator(gridSize, gridSize, horizAssignment[i])).generate(gridSize * gridSize);
      LazerField lf = new ContiguousLazerField(points);
      double[] bounds = Utils.getARange(points);
      double startProp = propAssignment[i] * 1.0 / numSegments;
      double endProp = startProp + 1.0 / numSegments;
      double d1 = ((bounds[1] - bounds[0]) * startProp) + bounds[0];
      double d2 = ((bounds[1] - bounds[0]) * endProp) + bounds[0];
      Point2D.Double a1 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d1);
      Point2D.Double a2 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d2);
      Path p1 = lf.getPathThroughField(a1);
      Path p2 = lf.getPathThroughField(a2);
      Area a = new SimpleFilling().enclosedByPaths(p1, p2);
      printer.setColor(getShadeOfTransparent(i % 2 == 0 ? Color.RED :Color.BLUE, 1.0 * (numSnakes - i) / (numSnakes + 1)));
      printer.fill(a);
    }
    printer.print("Plaid");
  }
  
  private static Color getShadeOfTransparent(Color c, double d) {
    return new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) Math.floor(d * 255));
  }

  private static void groovyMan() {
    SVGPrinter printer = new SVGPrinter();
    int gridSize = 8;
    int numSegments = 4;
    int numSnakes = 2 * numSegments;
    List<Integer> propAssignment = new ArrayList<>();
    List<Boolean> horizAssignment = new ArrayList<>();
    for (int i = 0; i < numSnakes; i++) {
      propAssignment.add(i % numSegments);
      horizAssignment.add(i % 2 == 0);
    }
    Collections.shuffle(propAssignment);
    Collections.sort(propAssignment, (a, b) -> Math.abs(b - numSegments / 2) - Math.abs(a - numSegments / 2));
    Collections.shuffle(horizAssignment);
    
    for (int i = 0; i < numSnakes; i++) {
      List<Point2D.Double> points = new PointListGenerator(
          new Point2D.Double(), 
          new RectangularGridSnakePointGenerator(gridSize, gridSize, horizAssignment.get(i))).generate((gridSize + 1) * (gridSize + 1));
      LazerField lf = new ContiguousLazerField(points);
      double[] bounds = Utils.getARange(points);
      double startProp = propAssignment.get(i) * 1.0 / numSegments;
      double endProp = startProp + 1.0 / numSegments;
      double d1 = ((bounds[1] - bounds[0]) * startProp) + bounds[0];
      double d2 = ((bounds[1] - bounds[0]) * endProp) + bounds[0];
      Point2D.Double a1 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d1);
      Point2D.Double a2 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d2);
      Path p1 = lf.getPathThroughField(a1);
      Path p2 = lf.getPathThroughField(a2);
      Area a = new SimpleFilling().enclosedByPaths(p1, p2);
      printer.setColor(randomColor());
      printer.fill(a);
    }
    printer.print("GroovyMan");
  }
    
  private static void firstSnakes() {
    SVGPrinter printer = new SVGPrinter();
    for (int i = 0; i < 4; i++) {
      List<Point2D.Double> points = new PointListGenerator(
          new Point2D.Double(), 
          new RectangularGridSnakePointGenerator(10, 10, true)).generate(100);
      LazerField lf = new ContiguousLazerField(points);
      double[] bounds = Utils.getARange(points);
      int numSegments = 4;
      double startProp = (i % numSegments) * 1.0 / numSegments;
      double endProp = startProp + 1.0 / numSegments;
      double d1 = ((bounds[1] - bounds[0]) * startProp) + bounds[0];
      double d2 = ((bounds[1] - bounds[0]) * endProp) + bounds[0];
      Point2D.Double a1 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d1);
      Point2D.Double a2 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d2);
      Path p1 = lf.getPathThroughField(a1);
      Path p2 = lf.getPathThroughField(a2);
      Area a = new SimpleFilling().enclosedByPaths(p1, p2);
      printer.setColor(randomColor());
      printer.fill(a);
    }
    printer.print("output18");
  }
    
    
  private static void snakesExample() {
    SVGPrinter printer = new SVGPrinter();
    for (int i = 0; i < 30; i++) {
      List<Point2D.Double> points = new PointListGenerator(
          new Point2D.Double(), 
          new RectangularGridRandomPointGenerator(1, 1),
          Constraints.noDuplicates(),
          Constraints.aRangeIsAtLeast(1)).generate(10);
      LazerField lf = new ContiguousLazerField(points);
      double[] bounds = Utils.getARange(points);
      int numSegments = 4;
      double startProp = (i % numSegments) * 1.0 / numSegments;
      double endProp = startProp + 1.0 / numSegments;
      double d1 = ((bounds[1] - bounds[0]) * startProp) + bounds[0];
      double d2 = ((bounds[1] - bounds[0]) * endProp) + bounds[0];
      Point2D.Double a1 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d1);
      Point2D.Double a2 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), d2);
      Path p1 = lf.getPathThroughField(a1);
      Path p2 = lf.getPathThroughField(a2);
      Area a = new SimpleFilling().enclosedByPaths(p1, p2);
      printer.setColor(randomColor());
      printer.fill(a);
    }
    printer.print("output17");
  }
  
  private static Color randomColor() {
    return new Color(
        (int) (Math.random() * 256),
        (int) (Math.random() * 256),
        (int) (Math.random() * 256));
        
  }
  
  public static void demonstrateFilling() {
    List<Point2D.Double> points = new PointListGenerator(new Point2D.Double(), new RandomRadiusPointGenerator(4, 2),
        Constraints.noNonAdjacentPointsAreCloserThan(4), Constraints.aRangeIsAtLeast(1))
            .generate(10);
    LazerField lf = new ContiguousLazerField(points);
    double[] bounds = Utils.getARange(points);
    Point2D.Double a1 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), bounds[0]);
    Point2D.Double a2 = PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), bounds[1]);
    Path p1 = lf.getPathThroughField(a1);
    Path p2 = lf.getPathThroughField(a2);
    Area a = new SimpleFilling().enclosedByPaths(p1, p2);
    SVGPrinter printer = new SVGPrinter();
    printer.setColor(Color.orange);
    printer.fill(a);
    printer.print("output16");
  }
    
  public static void demonstrateConstruction() {
    Construction c = new Construction();
    new FunkyStripedLazerField(3,
        new ContiguousLazerField(
            new PointListGenerator(new Point2D.Double(), new RandomRadiusPointGenerator(4, 2),
                Constraints.noNonAdjacentPointsAreCloserThan(4), Constraints.aRangeIsAtLeast(1))
                    .generate(10))).addToConstruction(c);
    c.doneDebug("output15.svg");
  }
}
