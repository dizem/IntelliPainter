package org.dizem.intellipainter.graphics;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-20
 * Time: 18:13:00
 */
public class Rect {
    public int left;
    public int top;
    public int right;
    public int bottom;

    public Rect() {
    }

    public Rect(int x1, int y1, int x2, int y2) {
        left = x1;
        top = y1;
        right = x2;
        bottom = y2;
    }

    public Rect(Point topLeft, Point bottomRight) {
        this(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);
    }

    public Rect(Rect r) {
        this(r.left, r.top, r.right, r.bottom);
    }

    public void update(int x2, int y2) {
        update(left, top, x2, y2);
    }

    public void update(int x1, int y1, int x2, int y2) {
        left = x1;
        top = y1;
        right = x2;
        bottom = y2;
    }

    @Override
    public String toString() {
        return "rect{left=" + left + ",top=" + top +
                ",right" + right + ",bottom=" + bottom + "}";
    }

    public void update(Point topLeft, Point bottomRight) {
        update(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);
    }

    public void modify() {
        int temp;
        if (left > right) {
            temp = left;
            left = right;
            right = temp;
        }
        if (top > bottom) {
            temp = top;
            top = bottom;
            bottom = temp;
        }
    }
}
