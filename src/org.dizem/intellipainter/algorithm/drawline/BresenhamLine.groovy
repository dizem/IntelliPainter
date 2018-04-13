package org.dizem.intellipainter.algorithm.drawline

import org.dizem.intellipainter.graphics.Point

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-24
 * Time: 19:13:25
 */
public class BresenhamLine {

    static Set<Point> getPoints(Point pStart, Point pEnd) {
        def result = new HashSet<Point>();
        int x = pStart.x;
        int y = pStart.y;
        int dx = Math.abs(pStart.x - pEnd.x);
        int dy = Math.abs(pStart.y - pEnd.y);

        int signX = pStart.x - pEnd.x > 0 ? -1 : 1;
        int signY = pStart.y - pEnd.y > 0 ? -1 : 1;
        Closure cls1 = { x += signX };
        Closure cls2 = { y += signY };

        if (dy >= dx) { //do swap
            (dx, dy) = [dy, dx];
            (cls1, cls2) = [cls2, cls1];
        }

        int nError = 2 * dy - dx;

        for (i in 0..dx) {
            result << new Point(x, y);
            if (nError >= 0) {
                cls2();
                nError -= 2 * dx;
            }
            cls1();
            nError += 2 * dy;
        }
        return result;
    }


}


