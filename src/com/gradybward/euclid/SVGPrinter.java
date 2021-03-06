package com.gradybward.euclid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.Document;

import com.gradybward.euclid.elements.Element;

public class SVGPrinter {

  private Color color;
  private final List<SVGPrinterElement> toWrite;

  public SVGPrinter() {
    color = Color.BLACK;
    toWrite = new ArrayList<>();
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public void draw(Element element) {
    toWrite.add(new SVGPrinterElement(color, element));
  }

  public void drawAll(List<Element> es) {
    for (Element e : es) {
      draw(e);
    }
  }

  public void fill(Area area) {
    toWrite.add(new SVGPrinterElement(color, area));
  }

  public void fill(List<Area> as) {
    for (Area a : as) {
      fill(a);
    }
  }

  public void print(String path) {
    print(path, 1000, 1000);
  }

  public void print(String path, int height, int width) {
    if (!path.endsWith(".svg")) {
      path = path + ".svg";
    }
    File outputFile = new File(path);
    Document document = GenericDOMImplementation.getDOMImplementation()
        .createDocument("http://www.w3.org/2000/svg", "svg", null);
    SVGGraphics2D svgGraphic = new SVGGraphics2D(document);

    double minX = getAllBounds().mapToDouble(p -> p.x).min().getAsDouble();
    double maxX = getAllBounds().mapToDouble(p -> p.x).max().getAsDouble();
    double minY = getAllBounds().mapToDouble(p -> p.y).min().getAsDouble();
    double maxY = getAllBounds().mapToDouble(p -> p.y).max().getAsDouble();

    double paddingProportion = .05;
    double maxDimension = Math.max(maxX - minX, maxY - minY);
    double scaleFactor = Math.min(width / ((maxX - minX) * (1 + paddingProportion)),
        height / ((maxY - minY) * (1 + paddingProportion)));
    AffineTransform translate = AffineTransform.getTranslateInstance(
        paddingProportion * maxDimension - minX, paddingProportion * maxDimension - minY);
    AffineTransform scale = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
    AffineTransform transform = AffineTransform.getTranslateInstance(0, 0);
    transform.concatenate(scale);
    transform.concatenate(translate);

    // svgGraphic.clipRect(0, 0, (int) (scaleFactor * (maxX - minX)), (int) (scaleFactor * (maxY -
    // minY)));
    svgGraphic.setStroke(new BasicStroke(1));

    for (SVGPrinterElement e : toWrite) {
      e.print(transform, svgGraphic);
    }

    try {
      PrintWriter pw = new PrintWriter(outputFile);
      svgGraphic.stream(pw, true);
      pw.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Stream<Point2D.Double> getAllBounds() {
    return toWrite.stream().flatMap(SVGPrinterElement::getBounds);
  }

  private static class SVGPrinterElement {
    private final Color color;
    private final Element element;
    private final Area area;

    public SVGPrinterElement(Color color, Element element) {
      this.color = color;
      this.element = element;
      this.area = null;
    }

    public SVGPrinterElement(Color color, Area area) {
      this.color = color;
      this.element = null;
      this.area = area;
    }

    public void print(AffineTransform transform, SVGGraphics2D svgGraphic) {
      svgGraphic.setColor(color);
      if (element != null) {
        element.render(transform, svgGraphic);
      }
      if (area != null) {
        svgGraphic.fill(transform.createTransformedShape(area));
      }
    }

    public Stream<Point2D.Double> getBounds() {
      if (element != null) {
        return element.getBounds();
      }
      return PointUtils.getBoundsFromRectangle((Rectangle2D.Double) area.getBounds2D());
    }
  }
}
