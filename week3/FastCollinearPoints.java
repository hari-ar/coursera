package week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by hari.l on 19/11/16.
 */


public class FastCollinearPoints {
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        // check corner cases
        if (points == null)
            throw new NullPointerException();

        Point[] copyOfPoints = points.clone();
        Arrays.sort(copyOfPoints);

        if (checkRedundantPoints(copyOfPoints)) {
            throw new IllegalArgumentException();
        }
        // and now show must go on )))

        for (int i = 0; i < copyOfPoints.length - 3; i++) {
            Arrays.sort(copyOfPoints);
            Arrays.sort(copyOfPoints, copyOfPoints[i].slopeOrder());
            int first = 1;
            for (int  index = 2; index < copyOfPoints.length; index++) {
                while (index < copyOfPoints.length && (copyOfPoints[0].slopeTo(copyOfPoints[first])== copyOfPoints[0].slopeTo(copyOfPoints[index]))) {
                    index++;
                }
                if (index - first >= 3 && copyOfPoints[0].compareTo(copyOfPoints[first]) < 0) {
                    lineSegments.add(new LineSegment(copyOfPoints[0], copyOfPoints[index - 1]));
                }
                first = index;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }


    private boolean checkRedundantPoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
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