package org.dizem.intellipainter.algorithm.clip;

import org.dizem.intellipainter.graphics.Line;
import org.dizem.intellipainter.graphics.Point;
import org.dizem.intellipainter.graphics.Rect;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-20
 * Time: 18:08:23
 */

//todo: fix bug: could not clip line which is absolutely outside the rectangle
public class CSClip {
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 4;
    public static final int TOP = 8;

    static int encode(int x, int y, Rect rect) {
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

    public static boolean clip(Line line, Rect rect) {

        int code1 = encode(line.a.x, line.a.y, rect);
        int code2 = encode(line.b.x, line.b.y, rect);
        int x = 0, y = 0;
        while (code1 != 0 || code2 != 0) {
            if ((code1 & code2) != 0) {
                line = null;
                return false;
            }
            int code = code1 == 0 ? code2 : code1;
            if ((LEFT & code) != 0) {
                x = rect.left;
                y = line.a.y + (line.b.y - line.a.y) * (rect.left - line.a.x) / (line.b.x - line.a.x);
            } else if ((RIGHT & code) != 0) {
                x = rect.right;
                y = line.a.y + (line.b.y - line.a.y) * (rect.right - line.a.x) / (line.b.x - line.a.x);
            } else if ((BOTTOM & code) != 0) {
                y = rect.bottom;
                x = line.a.x + (line.b.x - line.a.x) * (rect.bottom - line.a.y) / (line.b.y - line.a.y);
            } else if ((TOP & code) != 0) {
                y = rect.top;
                x = line.a.x + (line.b.x - line.a.x) * (rect.top - line.a.y) / (line.b.y - line.a.y);
            }

            if (code == code1) {
                line.a.x = x;
                line.a.y = y;
                code1 = encode(x, y, rect);
            } else {
                line.b.x = x;
                line.b.y = y;
                code2 = encode(x, y, rect);
            }
        }
        return true;
    }

    static boolean isOutside(Line l, Rect rect) {
        if (isOutside(l.a, rect) || isOutside(l.b, rect)) {
            return true;
        }
        return false;
    }

    static boolean isOutside(Point p, Rect rect) {
        if (p.x < rect.left || p.x > rect.right) {
            return true;
        }
        if (p.y < rect.top || p.y > rect.bottom) {
            return true;
        }
        return false;
    }
}