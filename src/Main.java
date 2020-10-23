import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.List;

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

public class Main {

  public static void main(String[] args) {
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
