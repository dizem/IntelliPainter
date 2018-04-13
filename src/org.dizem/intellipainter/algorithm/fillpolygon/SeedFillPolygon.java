package org.dizem.intellipainter.algorithm.fillpolygon;

import org.dizem.intellipainter.algorithm.drawline.DDALine;
import org.dizem.intellipainter.graphics.Line;
import org.dizem.intellipainter.graphics.Point;
import org.dizem.intellipainter.graphics.Polygon;

import java.awt.Graphics;
import java.awt.Color;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-20
 * Time: 14:16:27
 */
public class SeedFillPolygon {

    static boolean visited[][];
    static Graphics g;
    static int height;
    static int width;
    static int dx[] = {1, 0, -1, 0};
    static int dy[] = {0, 1, 0, -1};

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

        bfs(pStart);
    }

    static boolean inside(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    static void bfs(Point pStart) {
        LinkedList<Point> queue = new LinkedList<Point>();
        queue.add(pStart);
        visited[pStart.y][pStart.x] = true;
        g.fillOval(pStart.x - 1, pStart.y - 1, 2, 2);
        while (!queue.isEmpty()) {
            Point top = new Point(queue.poll());

            for (int i = 0; i < 4; ++i) {
                Point p = new Point(top.x + dx[i], top.y + dy[i]);
                if (inside(p.y, p.x) && !visited[p.y][p.x]) {
                    g.fillOval(p.x - 1, p.y - 1, 2, 2);
                    visited[p.y][p.x] = true;
                    queue.add(p);
                }
            }
        }
    }
}
