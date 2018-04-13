package org.dizem.intellipainter.algorithm.clip;

import org.dizem.intellipainter.graphics.Line;
import org.dizem.intellipainter.graphics.Rect;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-24
 * Time: 11:12:37
 */

//todo: there are some bugs in this algorithm.
public class MidPointClip {
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 4;
    public static final int TOP = 8;
    public static final double EPS = 1E-6;

    static int encode(double x, double y, Rect rect) {
        int c = 0;
        if (x < rect.left) {
            c |= LEFT;
        }
        if (x > rect.right) {
            c |= RIGHT;
        }
        if (y > rect.bottom) {
            c |= BOTTOM;
        }
        if (y < rect.top) {
            c |= TOP;
        }
        return c;
    }

    public static void clip(Line line, Rect rect) {
        double x1 = line.a.x;
        double y1 = line.a.y;
        double x2 = line.b.x;
        double y2 = line.b.y;
        int code1 = encode(x1, y1, rect);
        int code2 = encode(x2, y2, rect);
        if ((code1 | code2) == 0 || (code1 & code2) != 0) {
            return;
        } else if (code1 == 0 || code2 == 0) { // one is out of window
            if (code1 == 0) {
                clip(line, line.a.x, line.a.y, line.b.x, line.b.y, rect, true);
            } else {
                clip(line, line.b.x, line.b.y, line.a.x, line.a.y, rect, false);
            }

        } else { //both is out of window
            double x = (x1 + x2) / 2;
            double y = (y1 + y2) / 2;
            int code = encode(x, y, rect);
            while (code != 0) { //middle point is out of window
                if ((code1 & code) == 0) {
                    x2 = x;
                    y2 = y;
                    code2 = code;
                    line.b.x = (int) x;
                    line.b.y = (int) y;
                } else {
                    x1 = x;
                    y1 = y;
                    code1 = code;
                    line.a.x = (int) x;
                    line.a.y = (int) y;
                }
                x = (x1 + x2) / 2;
                y = (y1 + y2) / 2;
                code = encode(x, y, rect);
            }
            clip(line, x, y, x1, y1, rect, false);
            clip(line, x, y, x2, y2, rect, true);
        }
    }

    static void clip(Line line, double x1, double x2, double y1, double y2, Rect rect, boolean flag) {
        double x = (x1 + x2) / 2;
        double y = (y1 + y2) / 2;
        int code = encode(x, y, rect);
        while (Math.abs(x - x1) > EPS || Math.abs(y - y1) > EPS) {
            if (code == 0) {
                x1 = x;
                y1 = y;
            } else {
                x2 = x;
                y2 = y;
            }
            x = (x1 + x2) / 2;
            y = (y1 + y2) / 2;
            code = encode(x, y, rect);
        }
        if (flag) {
            line.b.x = (int) x;
            line.b.y = (int) y;
        } else {
            line.a.x = (int) x;
            line.a.y = (int) y;
        }
    }

}