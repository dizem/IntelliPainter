package org.dizem.intellipainter.graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-12
 * Time: 20:26:58
 */
public class Polygon {
    public List<Point> points = new ArrayList<Point>();
    public List<Line> lines = new ArrayList<Line>();

    public Polygon() {
    }

    public int nEdge() {
        return points.size();
    }

    void addPoint(Point p) {
        points.add(new Point(p));
    }

    public List<Line> getLines() {
        List<Line> ret = new ArrayList<Line>();
        for (int i = 0; i < points.size() - 1; ++i) {
            ret.add(new Line(points.get(i), points.get(i + 1)));
        }
        return ret;
    }

    public Point getFirst() {
        assert points.size() != 0;
        return points.get(0);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Polygon[n=" + points.size() + ", ");
        for (int i = 0; i < points.size(); ++i) {
            sb.append(points.get(i) + ", ");
        }
        return sb.toString();
    }

    public void initLines() {
        lines = getLines();
    }
}
