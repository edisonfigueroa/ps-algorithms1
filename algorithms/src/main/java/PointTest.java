import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class PointTest {

    @Test
    public void testSlopeTo() {
        Point point1 = new Point(32000, 10000);
        Point point2 = new Point(21000, 10000);
        Point point3 = new Point(1234, 5678);

        assertTrue(new Double(point1.slopeTo(point2)).equals(+0.0d));
        assertTrue(new Double(point1.slopeTo(point3)).equals(0.14047975037378924d));
    }

    @Test
    public void testSlopeComparator() {

        Point point1 = new Point(3100, 10917);
        Point point2 = new Point(3100, 18950);
        Point point3 = new Point(3100, 26778);
        Point point4 = new Point(3100, 29341);

        assertEquals(0, point1.SLOPE_ORDER.compare(point2, point3));
        assertEquals(0, point1.SLOPE_ORDER.compare(point3, point4));
        assertEquals(0, point1.SLOPE_ORDER.compare(point2, point4));
    }

    @Test
    public void testSlope() {
        // collinear points
        Point thePoint = new Point(13778, 4867);
        Point point1 = new Point(12987, 8031);
        Point point2 = new Point(10785, 16839);
        Point point3 = new Point(7682, 29251);

        // other collinear
        Point point4 = new Point(3100, 10917);
        Point point5 = new Point(3100, 18950);
        Point point6 = new Point(3100, 26778);
        Point point7 = new Point(3100, 29341);

        Point[] points = { thePoint, point3, point2, point1, point4, point7, point6,
            point5, };
        Arrays.sort(points, thePoint.SLOPE_ORDER);

        double slope1 = thePoint.slopeTo(point1);
        double slope2 = thePoint.slopeTo(point2);
        double slope3 = thePoint.slopeTo(point3);
        double slope4 = thePoint.slopeTo(point4);

        assertEquals(slope1, slope2);
    }

}
