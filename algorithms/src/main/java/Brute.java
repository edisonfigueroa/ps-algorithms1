import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Brute {

    private static final Pattern TWO_INTEGERS_PATTERN = Pattern
        .compile("\\s*(\\d+)\\s+(\\d+)\\s*");

    public static void main(String[] args) {
//        long now = new Date().getTime();
        new Brute().runCollinear(args[0]);
//        StdOut.println("Running time : " + (new Date().getTime() - now));
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

        In fileInput = new In(filePath);

        String line = fileInput.readLine();
        points = new Point[Integer.valueOf(line)];

        line = fileInput.readLine();
        int pointIndex = 0;
        while (line != null) {
            Matcher m = TWO_INTEGERS_PATTERN.matcher(line);
            if (m.find()) {
                points[pointIndex] = new Point(Integer.valueOf(m.group(1)), Integer
                    .valueOf(m.group(2)));
                points[pointIndex].draw();
                pointIndex++;
            }
            line = fileInput.readLine();
        }

        Arrays.sort(points);
        double currentSlope;

        for (int i = 0; i < (points.length - 3); i++) {

            for (int j = i + 1; j < points.length - 2; j++) {
                currentSlope = points[i].slopeTo(points[j]);

                for (int k = j + 1; k < points.length - 1; k++) {
                    if (currentSlope == points[j].slopeTo(points[k])) {
                        for (int l = k + 1; l < points.length; l++) {
                            if (currentSlope == points[k].slopeTo(points[l])) {
                                StdOut.println(points[i] + " -> " + points[j]
                                    + " -> " + points[k] + " -> " + points[l]);
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
