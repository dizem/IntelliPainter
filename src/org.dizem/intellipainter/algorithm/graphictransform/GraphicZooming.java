package org.dizem.intellipainter.algorithm.graphictransform;

import org.dizem.intellipainter.graphics.Point;
import org.dizem.intellipainter.graphics.Polygon;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-19
 * Time: 23:11:29
 */
public class GraphicZooming {
    public static void transform(Polygon g, Point pBase, int x, int id) {
        if (pBase == null) {
            throw new NullPointerException("Base should not be null");
        }

        List<Point> p = g.points;
        double zoom = (double) (x - pBase.x) / (p.get(id).x - pBase.x);
        if (zoom < 0.0001) {
            return;
        }
        for (int i = 0; i < p.size(); ++i) {
            p.get(i).x = (int) (zoom * (p.get(i).x - pBase.x) + pBase.x);
            p.get(i).y = (int) (zoom * (p.get(i).y - pBase.y) + pBase.y);
        }
    }
}
