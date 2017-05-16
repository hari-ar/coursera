package week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hari.l on 19/11/16.
 */


public class BruteCollinearPoints {
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
    if (points == null)
            throw new NullPointerException();

        Point[] copyOfPoints = points.clone();
        Arrays.sort(copyOfPoints);

        if (checkRedundantPoints(copyOfPoints)) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < copyOfPoints.length - 3; i++) {
            for (int j = i + 1; j < copyOfPoints.length - 2; j++) {
                double slopeij = copyOfPoints[i].slopeTo(copyOfPoints[j]);
                for (int k = j + 1; k < copyOfPoints.length - 1; k++) {
                    double slopeik = copyOfPoints[i].slopeTo(copyOfPoints[k]);
                    if (slopeij == slopeik) {
                        for (int l = k + 1; l < copyOfPoints.length; l++) {
                            double slopeil = copyOfPoints[i].slopeTo(copyOfPoints[l]);
                            if (slopeij == slopeil) {
                                lineSegments.add(new LineSegment(copyOfPoints[i], copyOfPoints[l]));
                            }
                        }
                    }
                }

            }
        }
    }

    private boolean checkRedundantPoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;

    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        try {
            for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                segment.draw();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        StdDraw.show();
    }
}
