package org.dizem.intellipainter.ui.bezierpainter

import javax.swing.JPanel
import org.dizem.intellipainter.graphics.Point
import java.awt.Color
import java.awt.Graphics
import org.dizem.intellipainter.ui.MainApp

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-24
 * Time: 17:22:07
 */
class BezierPainterPanel extends JPanel {
    BezierPainter painter
    Point pStart, pEnd, pCursor = new Point()

    List<Point> pList = new ArrayList<Point>()

    List<Point> bezierPoints
    BezierPainterMouseHandler handler

    BezierPainterPanel(BezierPainter painter) {
        this.painter = painter
        handler = new BezierPainterMouseHandler(this)
        addMouseMotionListener handler
        addMouseListener handler
    }

    void paint(Graphics g) {
        g.setColor(MainApp.colorBack)
        g.fillRect(0, 0, this.getWidth(), this.getHeight())

        switch (painter.state) {
            case handler.STATUS_DRAG_LINE:
                g.setColor(MainApp.colorPoint)
                g.drawLine(pStart.x, pStart.y, pEnd.x, pEnd.y)
                break
        }

        for (int i = 0; i < pList.size() - 1; ++i) {
            g.setColor(MainApp.colorPoint)
            g.drawLine(pList.get(i).x, pList.get(i).y, pList.get(i + 1).x, pList.get(i + 1).y)
            g.setColor Color.BLUE
            if (i == 0)
                g.fillOval(pList.get(i).x - 5, pList.get(i).y - 5, 10, 10)
            g.fillOval(pList.get(i + 1).x - 5, pList.get(i + 1).y - 5, 10, 10)
        }
        if (bezierPoints) {
            g.setColor Color.BLUE
            for (int i = 0; i < bezierPoints.size() - 1; ++i)
                g.drawLine(bezierPoints.get(i).x, bezierPoints.get(i).y,
                        bezierPoints.get(i + 1).x, bezierPoints.get(i + 1).y)

        }
    }

}