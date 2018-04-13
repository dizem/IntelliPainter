package org.dizem.intellipainter.algorithm.drawcircle;

import org.dizem.intellipainter.graphics.Point;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-24
 * Time: 19:58:20
 */
public class MPNCCircle {

    static Set<Point> getPoints(Point pCenter, Point pOther) {
        Set<Point> result = new HashSet<Point>();
        int r = (int) pCenter.distTo(pOther);
        int x = r, y = 0, f = 0, n = r;
        while (n > 0) {
            addPoint(result, pCenter, x, y);
            if (f <= 0) {
                f += 2 * y + 1;
                y++;
            } else {
                f += 1 - 2 * x;
                x--;
            }
            n--;
        }
        if (x == y) {
            addPoint(result, pCenter, x, y);
        }
        return result;
    }

    static int[] dirX = {1, -1, 1, -1};
    static int[] dirY = {1, 1, -1, -1};

    static void addPoint(Set<Point> result, Point pCenter, int x, int y) {
        for (int i = 0; i < 4; ++i) {
            result.add(new Point(pCenter.x + dirX[i] * x, pCenter.y + dirY[i] * y));
            result.add(new Point(pCenter.x + dirX[i] * y, pCenter.y + dirY[i] * x));
        }
    }

}
