package org.dizem.intellipainter.graphics.util;

import org.dizem.intellipainter.graphics.Line;
import org.dizem.intellipainter.graphics.Point;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-12
 * Time: 20:43:33
 */
public class GraphicLib {

    private static int sign(int x) {
        if (x < 0) {
            return -1;
        } else {
            return x > 0 ? 1 : 0;
        }
    }

    private static int xmult(Point p1, Point p2, Point p0) {
        return (p1.x - p0.x) * (p2.y - p0.y) - (p2.x - p0.x) * (p1.y - p0.y);
    }

    private static boolean dotsInline(Point p1, Point p2, Point p3) {
        return xmult(p1, p2, p3) == 0;
    }

    private static int sameSide(Point p1, Point p2, Point l1, Point l2) {
        return sign(xmult(l1, p1, l2)) * (xmult(l1, p2, l2) > 0 ? 1 : 0);
    }

    private static boolean dotOnlineIn(Point p, Point l1, Point l2) {
        return xmult(p, l1, l2) == 0 &&
                (l1.x - p.x) * (l2.x - p.x) <= 0 &&
                (l1.y - p.y) * (l2.y - p.y) <= 0;
    }

    private static boolean intersectIn(Point u1, Point u2, Point v1, Point v2) {
        if (!dotOnlineIn(u1, u2, v1) || !dotOnlineIn(u1, u2, v2)) {
            return sameSide(u1, u2, v1, v2) == 0 && sameSide(v1, v2, u1, u2) == 0;
        }
        return dotOnlineIn(u1, v1, v2) || dotOnlineIn(u2, v1, v2)
                || dotOnlineIn(v1, u1, u2) || dotOnlineIn(v2, u1, u2);
    }

    public static Point intersection(Line u, Line v) {
        Point ret = u.a;
        double t = ((u.a.x - v.a.x) * (v.a.y - v.b.y) - (u.a.y - v.a.y) * (v.a.x - v.b.x))
                / ((u.a.x - u.b.x) * (v.a.y - v.b.y) - (u.a.y - u.b.y) * (v.a.x - v.b.x));
        ret.x += (u.b.x - u.a.x) * t;
        ret.y += (u.b.y - u.a.y) * t;
        return ret;
    }

    public static boolean dotOnlineIn(Point p, Line l) {
        return xmult(p, l.a, l.b) == 0 &&
                (l.a.x - p.x) * (l.b.x - p.x) <= 0 &&
                (l.a.y - p.y) * (l.b.y - p.y) <= 0;
    }
}
