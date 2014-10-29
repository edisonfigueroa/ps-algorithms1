import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fast {

	private static final Pattern TWO_INTEGERS_PATTERN = Pattern
			.compile("\\p{javaWhitespace}*(\\d+)\\p{javaWhitespace}+(\\d+)\\p{javaWhitespace}*");

	public static void main(String[] args) {

		Point[] points = null;

		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);

		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledSquare(0, 0, 1);
		StdDraw.show();

		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);

		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			String line = br.readLine();
			points = new Point[Integer.valueOf(line)];

			for (int i = 0; (line = br.readLine()) != null; i++) {
				Matcher m = TWO_INTEGERS_PATTERN.matcher(line);
				if (m.find()) {
					points[i] = new Point(Integer.valueOf(m.group(1)),
							Integer.valueOf(m.group(2)));
					points[i].draw();
				} else {
					throw new IllegalArgumentException(
							"Line doesn't contain 2 integers: " + line);
				}
			}
			// line is not visible here.
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

		Arrays.sort(points);

		for (int i = 0; i < points.length; i++) {
			Point[] pointsCopy = Arrays.copyOf(points, points.length);

			Arrays.sort(pointsCopy, points[i].SLOPE_ORDER);

			int collinearCount = 0;
			int slopeIndex = 1;
			double slope = points[i].slopeTo(pointsCopy[slopeIndex]);

			while (true) {
				double nextSlope = points[i].slopeTo(pointsCopy[++slopeIndex]);
				if (slope == nextSlope) {
					collinearCount++;
				} else {
					break;
				}
			}
			if (collinearCount >= 3) {
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.setPenRadius(.0025);
				points[i].drawTo(points[collinearCount]);
			}
		}

	}
}
