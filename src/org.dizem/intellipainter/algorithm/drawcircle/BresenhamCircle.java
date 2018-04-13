package org.dizem.intellipainter.algorithm.drawcircle;

import org.dizem.intellipainter.graphics.Point;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-26
 * Time: 12:27:36
 */
public class BresenhamCircle {

	static Set<Point> getPoints(Point pCenter, Point pOther) {
		assert pCenter != null;
		assert pOther != null;

		Set<Point> result = new HashSet<Point>();
		int r = (int)pCenter.distTo(pOther);
		int x = 0, y = r, f = 3 - 2 * r;
		while(x < y) {
			addPoint(result, pCenter, x, y);
			f += f < 0 ? (4 * x++ + 6) : (4*(x++ - y--) + 10);
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
