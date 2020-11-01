import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.gradybward.colors.coolors.CoolorsColorPalletes;
import com.gradybward.euclid.PointUtils;
import com.gradybward.euclid.SVGPrinter;
import com.gradybward.euclid.elements.Path;
import com.gradybward.euclid.filling.FillingFromClosedPaths;
import com.gradybward.euclid.lazer.ContiguousLazerField;
import com.gradybward.euclid.lazer.LazerField;
import com.gradybward.gridtraversals.rectangles.RectangularGridCircut;
import com.gradybward.gridtraversals.rectangles.RectangularMetaGridCircutProvider;

public class Main {

  public static void main(String[] args) {
    circut();
  }
  
  private static void circut(){
    SVGPrinter printer = new SVGPrinter();
    com.gradybward.colors.Color[] colors = CoolorsColorPalletes.getRandomPallete().get();
    int[][] widths = new int[][] {{2, 6}, {6, 2}, {3, 5}, {2, 2, 2, 2}, {2, 3, 3}};
    int[][] heights = new int[][] {{5, 3}, {4, 2, 2}, {2, 4, 2}, {3, 3, 2}, {2, 2, 2, 2}};
    double[] acceptableRange = new double[]{1 - Math.sqrt(2) / 2 + .001, Math.sqrt(2) / 2 - .001}; 
    int numElements = 5;
    for (int i = 0; i < numElements; i++) {
      Optional<RectangularGridCircut> circut = RectangularMetaGridCircutProvider.findPathThroughMetaGrid(widths[i],  heights[i]);
      if (!circut.isPresent()) {
        throw new RuntimeException("Had issue generating: " + Arrays.toString(widths[i]) + " " + Arrays.toString(heights[i]));
      }
      List<Point2D.Double> points = circut.get().get();
      points.add(points.get(0));
      points.add(points.get(1));
      LazerField lf = new ContiguousLazerField(points);
      double startProp = 0 + (i * 1.0 / (numElements * 2 - 1));
      double endProp = 1 - (i * 1.0 / (numElements * 2 - 1));
      Path pathA = lf.getPathThroughField(PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), ((acceptableRange[1] - acceptableRange[0]) * startProp) + acceptableRange[0]));
      Path pathB = lf.getPathThroughField(PointUtils.pointFromLinePlusDistance(points.get(0), points.get(1), ((acceptableRange[1] - acceptableRange[0]) * endProp) + acceptableRange[0]));
      Area a = new FillingFromClosedPaths().enclosedByPaths(pathA, pathB);
      printer.setColor(colors[i].get());
      printer.fill(a);
    }
    printer.print("output/circut8.svg");
  }
}
