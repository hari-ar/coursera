package week5;

/**
 * Created by hari.l on 01/12/16.
 */


import edu.princeton.cs.algs4.*;

import java.awt.*;

public class KdTree {
    private static class KdTreeNode {
        private Point2D point;
        private RectHV rect;
        private KdTreeNode leftBottom;
        private KdTreeNode rightTop;

        public KdTreeNode(Point2D point, RectHV rect) {
            this.point = point;
            this.rect = rect;
            leftBottom = null;
            rightTop = null;
        }
    }

    private KdTreeNode root;
    private int size;
    private RectHV canvas = new RectHV(0, 0, 1, 1);

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private int compareTo(Point2D x, Point2D y, boolean isVertical) {
        if (x.equals(y)) return 0;
        if (isVertical) {
            if (x.x() < y.x()) return -1;
            else return 1;
        } else {
            if (x.y() < y.y()) return -1;
            else return 1;
        }
    }

    public void insert(Point2D p) {
        root = insert(root, p, true,
                canvas.xmin(), canvas.ymin(), canvas.xmax(), canvas.ymax());
    }

    private KdTreeNode insert(KdTreeNode kdTreeNode, Point2D point, boolean isVertical,
                              double xMin, double yMin, double xMax, double yMax) {
        if (kdTreeNode == null) {
            size++;
            return new KdTreeNode(point, new RectHV(xMin, yMin, xMax, yMax));
        }
        int cmpResult = compareTo(point, kdTreeNode.point, isVertical);
        double xMinTemp = xMin, yMinTemp = yMin, xMaxTemp = xMax, yMaxTemp = yMax;
        if (cmpResult < 0) {
            if (isVertical ) {
                xMaxTemp = kdTreeNode.point.x();
            }
            else {
                yMaxTemp = kdTreeNode.point.y();
            }
            kdTreeNode.leftBottom = insert(kdTreeNode.leftBottom, point, !isVertical, xMinTemp, yMinTemp, xMaxTemp, yMaxTemp);
        }
        if (cmpResult > 0) {
            if (isVertical) {
                xMinTemp = kdTreeNode.point.x();
            }
            else {
                yMinTemp = kdTreeNode.point.y();
            }
            kdTreeNode.rightTop = insert(kdTreeNode.rightTop, point, !isVertical, xMinTemp, yMinTemp, xMaxTemp, yMaxTemp);
        }
        return kdTreeNode;
    }

    public boolean contains(Point2D p) {
        return contains(root, p, true);
    }

    private boolean contains(KdTreeNode node, Point2D point, boolean isVertical) {
        if (node == null) return false;
        int cmpResult = compareTo(point, node.point, isVertical);
        if (cmpResult < 0)
            return contains(node.leftBottom, point, !isVertical);
        if (cmpResult > 0)
            return  contains(node.rightTop, point, !isVertical);
        return true;
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(KdTreeNode node, boolean isVertical) {
        if (node != null) {
            StdDraw.setPenColor(Color.BLACK);
            node.point.draw();
            if (isVertical) {
                StdDraw.setPenColor(Color.RED);
                StdDraw.line(node.point.x(),node.rect.ymin(),node.point.x(),node.rect.ymax());
            } else {
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.line(node.rect.xmin(),node.point.y(),node.rect.xmax(),node.point.y());
            }
            draw(node.leftBottom, !isVertical);
            draw(node.rightTop, !isVertical);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> inPointQ = new Queue<Point2D>();
        Queue<KdTreeNode> needSearchArea = new Queue<KdTreeNode>();
        if (root == null) return inPointQ;
        needSearchArea.enqueue(root);
        while (!needSearchArea.isEmpty()) {
            KdTreeNode note = needSearchArea.dequeue();
            // if (rect.distanceSquaredTo(note.point) == 0) inPointQ.enqueue(note.point);
            if (rect.contains(note.point)) inPointQ.enqueue(note.point);
            if (note.leftBottom != null && rect.intersects(note.leftBottom.rect))
                needSearchArea.enqueue(note.leftBottom);
            if (note.rightTop != null && rect.intersects(note.rightTop.rect))
                needSearchArea.enqueue(note.rightTop);
        }
        return inPointQ;
    }

    public Point2D nearest(Point2D point) {
        if (root == null) return null; //Check if root present
        return nearest(point, root, root.point, true); // If present start with root node
    }


    private Point2D nearest(Point2D point, KdTreeNode node, Point2D tempClosePoint, boolean compareX) {
        if (node == null) return tempClosePoint;
        // if node point is closer than tempClosePoint, it becomes the new tempClosePoint
        if (point.distanceSquaredTo(node.point) < point.distanceSquaredTo(tempClosePoint)) {
            tempClosePoint = node.point;
        }
        // only explore node if it can contain a point closer than the tempClosePoint
        if (node.rect.distanceSquaredTo(point) < point.distanceSquaredTo(tempClosePoint)) {
            // query point is left or below node point
            if ((compareX && point.x() < node.point.x()) ||
                    (!compareX && point.y() < node.point.y())) {
                // explore left bottom first
                tempClosePoint = nearest(point, node.leftBottom, tempClosePoint, !compareX);
                tempClosePoint = nearest(point, node.rightTop, tempClosePoint, !compareX);
            } else {
                // query point is right, above, or equal to node point
                // explore right top first
                tempClosePoint = nearest(point, node.rightTop, tempClosePoint, !compareX);
                tempClosePoint = nearest(point, node.leftBottom, tempClosePoint, !compareX);
            }
        }
        return tempClosePoint;
    }
    public static void main(String[] args) {

    }
}