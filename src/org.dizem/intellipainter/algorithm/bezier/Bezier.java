package org.dizem.intellipainter.algorithm.bezier;

import org.dizem.intellipainter.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-24
 * Time: 17:02:27
 */
public class Bezier {

    static double carry(double n, double k) {
        double ans = 1;
        if (k > n / 2) {
            k = n - k;
        }
        for (double i = 0; i < k; i++) {
            ans *= (n - i) / (k - i);
        }
        return ans;
    }

    static Point dimension(List<Point> list, double t) {
        int n = list.size() - 1;
        double x = 0.0, y = 0.0;
        for (int j = 0; j <= n; j++) {
            double rate = carry(n, j) * Math.pow(t, j) * Math.pow((1.0 - t), (n - j));
            x += rate * list.get(j).x;
            y += rate * list.get(j).y;
        }
        return new Point((int) x, (int) y);
    }

    static List<Point> getPoints(List<Point> list) throws IllegalAccessException {
        if (list.size() < 2) {
            throw new IllegalAccessException("Point list size should be larger than 2.");
        }
        List<Point> set = new ArrayList<Point>();
        for (double t = 0; t <= 1; t += 0.01) {
            set.add(dimension(list, t));
        }
        return set;
    }
}
