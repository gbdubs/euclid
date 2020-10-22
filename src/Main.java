import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.gradybward.euclid.Construction;
import com.gradybward.euclid.lazer.ContiguousLazerField;
import com.gradybward.euclid.lazer.SomeStripedLazerField;
import com.gradybward.pointlist.RadialMDBLPLG;

public class Main {

  public static void main(String[] args) {
    List<Point2D.Double> points1 = new RadialMDBLPLG(new Point2D.Double(), new ArrayList<>(), 4.0,
        6.0).generate(30);
    SomeStripedLazerField slf1 = new SomeStripedLazerField(new ContiguousLazerField(points1), true, true, false);
    Construction c = new Construction();
    slf1.addToConstruction(c);
    c.doneDebug("output10.svg");
  }
}
