package org.dizem.intellipainter.algorithm.drawcircle;

import org.dizem.intellipainter.graphics.Point;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-27
 * Time: 16:53:01
 */
public class MidCircle {

	static int[] dirX = {1, -1, 1, -1};
	static int[] dirY = {1, 1, -1, -1};

	static void addPoint(Set<Point> result, Point pCenter, int x, int y) {
		for(int i=0; i<4; ++i) {
			result.add(new Point(pCenter.x + dirX[i]*x, pCenter.y + dirY[i]*y));
			result.add(new Point(pCenter.x + dirX[i]*y, pCenter.y + dirY[i]*x));
		}
	}

	static Set<Point> getPoints(Point pCenter, Point pOther) {
		Set<Point> result = new HashSet<Point>();
		int r = (int)(pOther.distTo(pCenter));
		int x = 0, y = r;
		int d = 1 - r;
		while(y > x) {
			addPoint(result, pCenter, x, y);
			if(d < 0)
				d += 2*x + 3;
			else {
				d += 2*(x - y) + 5;
				y--;
			}
			x++;
		}
		if(x == y)
			addPoint(result, pCenter, x, y);
		return result;
	}
}
