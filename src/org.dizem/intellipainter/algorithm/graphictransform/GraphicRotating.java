package org.dizem.intellipainter.algorithm.graphictransform;

import org.dizem.intellipainter.graphics.Point;
import org.dizem.intellipainter.graphics.Polygon;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-19
 * Time: 21:57:15
 */
public class GraphicRotating {

    static boolean isClockwise(Point base, Point pStart, Point pEnd) {
        int x1 = pStart.x - base.x;
        int x2 = pEnd.x - base.x;
        int y1 = pStart.y - base.y;
        int y2 = pEnd.y - base.y;
        return x1 * y2 - x2 * y1 > 0;
    }

    public static void transform(Polygon g, Point base, Point pStart, Point pEnd) {
        double a = pStart.distTo(base);
        double b = pEnd.distTo(base);
        double c = pStart.distTo(pEnd);
        double angle = Math.acos((a * a + b * b - c * c) / (2 * a * b));
        if (!isClockwise(base, pStart, pEnd)) {
            angle *= -1;
        }
        assert base != null;
        Point zero = new Point();
        GraphicMoving.transform(g, base, zero);
        for (int i = 0; i < g.points.size(); ++i) {
            int x = g.points.get(i).x;
            int y = g.points.get(i).y;
            g.points.get(i).x = (int) (x * Math.cos(angle) - y * Math.sin(angle));
            g.points.get(i).y = (int) (x * Math.sin(angle) + y * Math.cos(angle));
        }
        GraphicMoving.transform(g, zero, base);
    }

    public static void transform(Polygon g, Point base, double angle) {

        assert base != null;
        Point zero = new Point();
        GraphicMoving.transform(g, base, zero);
        for (int i = 0; i < g.points.size(); ++i) {
            int x = g.points.get(i).x;
            int y = g.points.get(i).y;
            g.points.get(i).x = (int) (x * Math.cos(angle) - y * Math.sin(angle));
            g.points.get(i).y = (int) (x * Math.sin(angle) + y * Math.cos(angle));
        }
        GraphicMoving.transform(g, zero, base);
    }
}
