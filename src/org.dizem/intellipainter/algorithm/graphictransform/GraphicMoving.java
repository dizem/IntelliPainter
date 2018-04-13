package org.dizem.intellipainter.algorithm.graphictransform;

import org.dizem.intellipainter.graphics.Point;
import org.dizem.intellipainter.graphics.Polygon;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-19
 * Time: 21:31:35
 */
public class GraphicMoving {
    public static void transform(Polygon g, Point base, Point direction) {
        if (base == null || direction == null) {
            throw new NullPointerException("Point should not be null");
        }
        int x = direction.x - base.x;
        int y = direction.y - base.y;
        for (int i = 0; i < g.points.size(); ++i) {
            g.points.get(i).x += x;
            g.points.get(i).y += y;
        }
    }
}
