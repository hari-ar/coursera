package week5;

/**
 * Created by aahuya.rakshaka on 16/05/17.
 */

import java.util.TreeSet;
import edu.princeton.cs.algs4.*;

public class PointSET {
    private TreeSet<Point2D> pointSet;

    public PointSET() {
        pointSet = new TreeSet<Point2D>();
    }


    public void insert(Point2D point) {
        pointSet.add(point);
    }



    public void draw() {

        for (Point2D localPoint : pointSet)
            StdDraw.point(localPoint.x(), localPoint.y());
    }

    public Iterable<Point2D> range(RectHV rect) {
        TreeSet<Point2D> rangeSet = new TreeSet<Point2D>();
        for (Point2D localPoint : pointSet) {
            if (rect.contains(localPoint))
                rangeSet.add(localPoint);
        }
        return rangeSet;
    }

    public Point2D nearest(Point2D point) { // Calculate nearest point
        Point2D nearestPoint = null;
        double minDistance = Double.MAX_VALUE;
        for (Point2D localPoint : pointSet) {
            double distance = localPoint.distanceTo(point);
            if (distance < minDistance) {
                nearestPoint = localPoint;
                minDistance = distance;
            }
        }
        return nearestPoint;
    }

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    public boolean contains(Point2D point) {
        return pointSet.contains(point);
    }

    public int size() {
        return pointSet.size();
    }


    public static void main(String[] args) {

    }
}