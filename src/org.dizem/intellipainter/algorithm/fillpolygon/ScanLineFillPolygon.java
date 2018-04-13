package org.dizem.intellipainter.algorithm.fillpolygon;

import org.dizem.intellipainter.algorithm.drawline.DDALine;
import org.dizem.intellipainter.graphics.Line;
import org.dizem.intellipainter.graphics.Point;
import org.dizem.intellipainter.graphics.Polygon;

import java.awt.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-12
 * Time: 20:23:48
 */
public class ScanLineFillPolygon {

    static boolean visited[][];
    static Graphics g;
    static int height;
    static int width;

    public static void fill(Graphics graphics, Color c, Polygon p, Point pStart, int h, int w) {
        assert pStart != null;
        g = graphics;
        height = h;
        width = w;
        g.setColor(c);
        visited = new boolean[w][h];
        for (Line line : p.getLines()) {
            for (Point point : DDALine.getPoints(line.a, line.b)) {
                visited[point.y][point.x] = true;
            }
        }
        scanLine(pStart);
    }

    static void scanLine(Point pStart) {
        int xl, xr, x, y;
        boolean spanNeedFill;
        visited[pStart.y][pStart.x] = true;
        Stack<Point> stack = new Stack<Point>();
        stack.push(new Point(pStart));

        while (!stack.isEmpty()) {
            Point p = new Point(stack.pop());
            y = p.y;
            x = p.x;

            while (!visited[y][x]) {
                visited[y][x] = true;
                g.fillOval(x - 1, y - 1, 2, 2);
                x++;
            }
            xr = x - 1;
            x = p.x - 1;
            while (!visited[y][x]) {
                visited[y][x] = true;
                g.fillOval(x - 1, y - 1, 2, 2);
                x--;
            }
            xl = x + 1;
            x = xl;
            y++;

            while (x < xr) {
                spanNeedFill = false;
                while (!visited[y][x]) {
                    spanNeedFill = true;
                    x++;
                }
                if (spanNeedFill) {
                    stack.push(new Point(x - 1, y));
                    spanNeedFill = false;
                }
                x++;
            }
            x = xl;
            y -= 2;

            while (x < xr) {
                spanNeedFill = false;
                while (!visited[y][x]) {
                    spanNeedFill = true;
                    x++;
                }
                if (spanNeedFill) {
                    stack.push(new Point(x - 1, y));
                    spanNeedFill = false;
                }
                x++;
            }
        }
    }

}