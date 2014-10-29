import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

public class Brute {

	private static final Pattern TWO_INTEGERS_PATTERN = Pattern
			.compile("\\p{javaWhitespace}*(\\d+)\\p{javaWhitespace}+(\\d+)\\p{javaWhitespace}*");

	public static void main(String[] args) {
		long now = new Date().getTime();
		new Brute().runCollinear(args[0]);
		StdOut.println("Running time : " + (new Date().getTime() - now));
	}

	private void runCollinear(String filePath) {
		Point[] points = null;

		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);

		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledSquare(0, 0, 1);
		StdDraw.show();

		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.005);

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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
		double currentSlope;

		for (int i = 0; i < (points.length - 3); i++) {

			DecimalFormat df = new DecimalFormat("#.##");
			StdOut.println("" + df.format((((double)i+1)/((points.length - 2)))*100) + " %");

			for (int j = i + 1; j < points.length - 2; j++) {
				currentSlope = points[i].slopeTo(points[j]);

				for (int k = j + 1; k < points.length - 1; k++) {
					if (currentSlope == points[j].slopeTo(points[k])) {
						for (int l = k + 1; l < points.length; l++) {
							if (currentSlope == points[k].slopeTo(points[l])) {
								StdOut.println(points[i] + " -> " + points[j]
										+ " -> " + points[k] + " -> "
										+ points[l]);
								StdDraw.setPenColor(StdDraw.BLUE);
								StdDraw.setPenRadius(.0025);
								points[i].drawTo(points[l]);
							}
						}
					}
				}
			}
		}

	}
}
