package org.dizem.intellipainter.algorithm.drawline;

import org.dizem.intellipainter.graphics.Point;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-25
 * Time: 20:34:16
 */
public class MidLine {

    static Set<Point> getPoints(Point pStart, Point pEnd) {
        Set<Point> result = new HashSet<Point>();

        if (pStart.x > pEnd.x) {
            Point temp = pStart;
            pStart = pEnd;
            pEnd = temp;
        }

        int dx = pEnd.x - pStart.x;
        int dy = pEnd.y - pStart.y;

        if (dx == 0) {
            for (int i = Math.min(pStart.y, pEnd.y); i < Math.max(pStart.y, pEnd.y); ++i) {
                result.add(new Point(pStart.x, i));
            }
            return result;
        }
        if (dy == 0) {
            for (int i = Math.min(pStart.x, pEnd.x); i < Math.max(pStart.x, pEnd.x); ++i) {
                result.add(new Point(i, pStart.y));
            }
            return result;
        }

        int x = pStart.x;
        int y = pStart.y;

        result.add(new Point(x, y));

        while (dx >= dy && dy > 0) { //0 < scope < =1
            int d = dy * 2 - dx;
            while (x < pEnd.x) {
                if (d > 0) {
                    d += (dy - dx) * 2;
                    ++x;
                    ++y;
                } else {
                    d += dy * 2;
                    ++x;
                }
                result.add(new Point(x, y));
            }
            return result;
        }

        if (dy > dx && dy > 0) { //scope > 1
            int d = dy - (dx * 2);
            while (y < pEnd.y) {
                if (d < 0) {
                    d += (dy - dx) * 2;
                    ++x;
                    ++y;
                } else {
                    d += -dx * 2;
                    ++y;
                }
                result.add(new Point(x, y));
            }
            return result;
        }

        if (dx > Math.abs(dy) && dy < 0) {    // -1 <= scope < 0
            int d = dy * 2 + dx;
            while (x < pEnd.x) {
                if (d < 0) {
                    d += (dy + dx) * 2;
                    ++x;
                    --y;
                } else {
                    d += dy * 2;
                    ++x;
                }
                result.add(new Point(x, y));
            }
            return result;
        }

        if (Math.abs(dy) > dx && dy < 0) {// scope < -1
            int d = dy + dx * 2;
            while (y > pEnd.y) {
                if (d > 0) {
                    d += (dy + dx) * 2;
                    ++x;
                    --y;
                } else {
                    d += dx * 2;
                    --y;
                }
                result.add(new Point(x, y));
            }
            return result;
        }
        return null;
    }
}
