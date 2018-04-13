package org.dizem.intellipainter.algorithm.drawline;

import org.dizem.intellipainter.graphics.Point;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-23
 * Time: 9:05:48
 */
public class DDALine {

    public static Set<Point> getPoints(Point pStart, Point pEnd) {
        Set<Point> result = new HashSet<Point>();
        double x = pStart.x;
        double y = pStart.y;

        if (Math.abs(pStart.x - pEnd.x) < 1E-6) {
            for (int i = Math.min(pStart.y, pEnd.y); i < Math.max(pStart.y, pEnd.y); ++i) {
                result.add(new Point(pStart.x, i));
            }
            return result;
        }

        double scope = (double) (pEnd.y - pStart.y) / (pEnd.x - pStart.x);
        if (Math.abs(scope) <= 1) {
            if (pStart.x <= pEnd.x) {
                for (int i = pStart.x; i <= pEnd.x; i++) {
                    result.add(new Point(i, (int) (y + 0.5)));
                    y += scope;
                }
            } else {
                for (int i = pStart.x; i >= pEnd.x; i--) {
                    result.add(new Point(i, (int) (y + 0.5)));
                    y -= scope;
                }
            }
        } else {
            if (pStart.y <= pEnd.y) {
                for (int i = pStart.y; i <= pEnd.y; i++) {
                    result.add(new Point((int) (x + 0.5), i));
                    x += 1.0 / scope;
                }
            } else {
                for (int i = pStart.y; i >= pEnd.y; i--) {
                    result.add(new Point((int) (x + 0.5), i));
                    x -= 1.0 / scope;
                }
            }
        }
        return result;
    }

}