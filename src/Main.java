import java.awt.geom.Point2D;

import com.gradybward.euclid.Construction;
import com.gradybward.euclid.lazer.ContiguousLazerField;
import com.gradybward.euclid.lazer.FunkyStripedLazerField;
import com.gradybward.pointlist.Constraints;
import com.gradybward.pointlist.PointListGenerator;
import com.gradybward.pointlist.RandomRadiusPointGenerator;

public class Main {

  public static void main(String[] args) {
    Construction c = new Construction();
    new FunkyStripedLazerField(3,
        new ContiguousLazerField(
            new PointListGenerator(new Point2D.Double(), new RandomRadiusPointGenerator(4, 2),
                Constraints.noNonAdjacentPointsAreCloserThan(4), Constraints.aRangeIsAtLeast(1))
                    .generate(10))).addToConstruction(c);
    c.doneDebug("output14.svg");
  }
}
