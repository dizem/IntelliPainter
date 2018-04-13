package org.dizem.intellipainter.graphics;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-13
 * Time: 14:54:31
 */
public class Line {
    public Point a, b;

    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public Line(int x1, int y1, int x2, int y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    @Override
    public String toString() {
        return "Line{" + a + " - " + b + "}";
    }
}
