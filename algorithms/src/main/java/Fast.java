import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fast {

    private static final Pattern TWO_INTEGERS_PATTERN = Pattern
        .compile("\\s*(\\d+)\\s+(\\d+)\\s*");

    public static void main(String[] args) {
//        long now = new java.util.Date().getTime();
        new Fast().runCollinear(args[0]);
//        StdOut.println("Running time : " + (new java.util.Date().getTime() - now));
    }

    private void runCollinear(String filePath) {

        Point2[] points = null;

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledSquare(0, 0, 1);
        StdDraw.show();

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.005);

        In fileInput = new In(filePath);

        String line = fileInput.readLine();
        points = new Point2[Integer.valueOf(line)];

        line = fileInput.readLine();
        int pointIndex = 0;
        while (line != null) {
            Matcher m = TWO_INTEGERS_PATTERN.matcher(line);
            if (m.find()) {
                points[pointIndex] = new Point2(Integer.valueOf(m.group(1)), Integer
                    .valueOf(m.group(2)));
                points[pointIndex].draw();
                pointIndex++;
            }
            line = fileInput.readLine();
        }

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(.0025);

        Arrays.sort(points);
        Set<LineKey> linesCache = new HashSet<LineKey>();
        SlopeComparator2 slopeComparator = new SlopeComparator2();
        
        for (int i = 0; i < points.length-4; i++) {
            Point2 thePoint = points[i];
            slopeComparator.setThePoint(thePoint);

            Arrays.sort(points, i, points.length, slopeComparator);

            Map<Double, Set<Point2>> collinearSetsBySlope = slopeComparator
                .getColinearPoints();

            for (Entry<Double, Set<Point2>> entry : collinearSetsBySlope.
                entrySet()) {
                Set<Point2> collinearSets = entry.getValue();

                collinearSets.add(thePoint);

                if (collinearSets.size() >= 4) {
                    Point2[] collinearPoints = new Point2[collinearSets.size()];
                    collinearSets.toArray(collinearPoints);
                    Arrays.sort(collinearPoints);

                    LineKey lineKey = 
                        new LineKey(collinearPoints[collinearPoints.length-1], 
                                    entry.getKey());
                    if (!linesCache.contains(lineKey)) {
                        linesCache.add(lineKey);
                        StringBuilder collinearString = new StringBuilder();
                        collinearString.append(collinearPoints[0]);
                        for (int j = 1; j < collinearPoints.length; j++) {
                            collinearString.append(" -> ");
                            collinearString.append(collinearPoints[j]);
                        }
                        StdOut.println(collinearString.toString());
                        collinearPoints[0]
                            .drawTo(collinearPoints[(collinearPoints.length - 1)]);
                    }
                }
            }
        }
    }

    private class Point2 implements Comparable<Point2> {
        private final int x; // x coordinate
        private final int y; // y coordinate

        // create the point (x, y)
        public Point2(int x, int y) {
            /* DO NOT MODIFY */
            this.x = x;
            this.y = y;
        }

        // plot this point to standard drawing
        public void draw() {
            /* DO NOT MODIFY */
            StdDraw.point(x, y);
        }

        // draw line between this point and that point to standard drawing
        public void drawTo(Point2 that) {
            /* DO NOT MODIFY */
            StdDraw.line(this.x, this.y, that.x, that.y);
        }

        // slope between this point and that point
        public double slopeTo(Point2 that) {
            if (this.y == that.y) {
                if (this.x == that.x) {
                    return Double.NEGATIVE_INFINITY;
                } else {
                    return +0.0;
                }
            } else {
                if (this.x == that.x) {
                    return Double.POSITIVE_INFINITY;
                } else {
                    return ((double) this.y - that.y) / ((double) this.x - that.x);
                }
            }
        }

        // is this point lexicographically smaller than that one?
        // comparing y-coordinates and breaking ties by x-coordinates
        public int compareTo(Point2 that) {
            if (this.y == that.y) {
                if (this.x < that.x) {
                    return -1;
                } else if (this.x > that.x) {
                    return 1;
                } else {
                    return 0;
                }
            } else if (this.y < that.y) {
                return -1;
            } else {
                return 1;
            }
        }

        // return string representation of
        // thisSlopeComparatorSlopeComparatorSlopeComparator point
        public String toString() {
            /* DO NOT MODIFY */
            return "(" + x + ", " + y + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Point2 other = (Point2) obj;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }
    }

    private class SlopeComparator2 implements Comparator<Point2> {

        private Point2 thePoint;

        private Map<Double, Set<Point2>> collinearPoints = 
            new HashMap<Double, Set<Point2>>();

        public void setThePoint(Point2 thePoint) {
            this.thePoint = thePoint;
            collinearPoints.clear();
        }

        public int compare(Point2 o1, Point2 o2) {
            double slope1 = thePoint.slopeTo(o1);
            double slope2 = thePoint.slopeTo(o2);
            if (slope1 < slope2) {
                return -1;
            } else if (slope1 > slope2) {
                return 1;
            } else {
                Set<Point2> slopeSet = collinearPoints.get(slope1);
                if (slopeSet == null) {
                    slopeSet = new HashSet<Point2>();
                    collinearPoints.put(slope1, slopeSet);
                }
                slopeSet.add(o1);
                slopeSet.add(o2);
                return 0;
            }
        }

        public Map<Double, Set<Point2>> getColinearPoints() {
            return collinearPoints;
        }
    }

    private static class LineKey {

        private Point2 lastPoint;
        private double slope;

        public LineKey(Point2 point, Double slope) {
            this.lastPoint = point;
            this.slope = Math.abs(slope);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;

            int pointHash = 0;
            if (lastPoint != null) {
                pointHash = lastPoint.hashCode();
            }
            result = prime * result + pointHash;
            long temp;
            temp = Double.doubleToLongBits(slope);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            LineKey other = (LineKey) obj;
            if (lastPoint == null) {
                if (other.lastPoint != null)
                    return false;
            } else if (!lastPoint.equals(other.lastPoint))
                return false;
            if (Double.doubleToLongBits(slope) != Double
                .doubleToLongBits(other.slope))
                return false;
            return true;
        }
    }
}
