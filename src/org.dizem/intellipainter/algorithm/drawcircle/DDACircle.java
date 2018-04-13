package org.dizem.intellipainter.algorithm.drawcircle;

import org.dizem.intellipainter.graphics.Point;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-29
 * Time: 10:51:01
 */
class DDACircle {


	static Set<Point> getPoints(Point pCenter, Point pOther) {
		Set<Point> result = new HashSet<Point>();

		int r = (int)pCenter.distTo(pOther);
		int x = 0, y = r, n = r;
		double cr = r;
		double z = 1.0 / cr;
		double a = x, b = y;

		while(n > 0) {
			addPoint(result, pCenter, x, y);
			int temp = (int)a;
			a -= b * z;
			b += temp * z;
			x = (int)a;
			y = (int)b;
			n--;
		}
		if(x == y) {
			addPoint(result, pCenter, x, y);
		}
		return result;
	}

	static int[] dirX = {1, -1, 1, -1};
	static int[] dirY = {1, 1, -1, -1};

	static void addPoint(Set<Point> result, Point pCenter, int x, int y) {
		for(int i=0; i<4; ++i) {
			result.add(new Point(pCenter.x + dirX[i]*x, pCenter.y + dirY[i]*y));
			result.add(new Point(pCenter.x + dirX[i]*y, pCenter.y + dirY[i]*x));
		}
	}

}
