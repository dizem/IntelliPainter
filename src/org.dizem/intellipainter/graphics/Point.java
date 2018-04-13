/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package org.dizem.intellipainter.graphics;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-23
 * Time: 9:01:45
 */

public class Point {
    public int x;
    public int y;
    private static final int CLOSE_DIST = 10;

    public Point(int px, int py) {
        x = px;
        y = py;
    }

    public Point() {
        this(0, 0);
    }

    public Point(Point a) {
        this(a.x, a.y);
    }

    public Point(Point a, Point b) {
        this((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

    @Override
    public int hashCode() {
        return x + y * 100000000;
    }

    @Override
    public boolean equals(Object obj) {
        return x == ((Point) obj).x && y == ((Point) obj).y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public double distTo(Point other) {
        return Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y));
    }

    public boolean closeTo(Point other) {
        return distTo(other) < CLOSE_DIST;
    }

}
