import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PointTest {

	@Test
	public void testSlopeTo() {
		Point point1 = new Point(32000, 10000);
		Point point2 = new Point(21000, 10000);
		Point point3 = new Point(1234, 5678);

		assertTrue(new Double(point1.slopeTo(point2)).equals(+0.0d));
		assertTrue(new Double(point1.slopeTo(point3))
				.equals(0.14047975037378924d));
	}

}
