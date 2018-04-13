package org.dizem.intellipainter.ui.bezierpainter

import java.awt.event.MouseListener
import java.awt.event.MouseAdapter
import org.dizem.intellipainter.logger.Log
import java.awt.event.MouseEvent
import org.dizem.intellipainter.graphics.Point

import org.dizem.intellipainter.algorithm.bezier.Bezier
import java.awt.event.InputEvent

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-24
 * Time: 17:25:22
 */
class BezierPainterMouseHandler extends MouseAdapter implements MouseListener {
    public static final int STATUS_BEGIN = 0
    public static final int STATUS_DRAG_LINE = 1
    public static final int STATUS_BEZIER = 2
    public static final int STATUS_MOVE_POINT = 3

    BezierPainter painter
    BezierPainterPanel panel
    Log log

    BezierPainterMouseHandler(BezierPainterPanel panel) {
        this.panel = panel
        this.painter = panel.painter
        log = painter.log
    }


    void changeStateTo(int state) {
        switch (state) {
            case STATUS_BEGIN:
                painter.state = state
                painter.setTitle('Bezier - Please select some points(End by click right button)')
                log.info("state changed to STATUS_BEGIN")
                break

            case STATUS_DRAG_LINE:
                painter.state = state
                break

            case STATUS_MOVE_POINT:
                painter.state = state
                log.info("state changed to STATUS_MOVE_POINT")
                break

            case STATUS_BEZIER:
                painter.state = state
                painter.setTitle('Bezier - Select a point to move')
                log.info("state changed to STATUS_BEZIER")
                break
        }
    }


    void mouseClicked(MouseEvent e) {

        int x = (int) e.x;
        int y = (int) e.y;

        log.info "Click At ($x, $y)"

        if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
            if (painter.state == STATUS_DRAG_LINE) {
                panel.bezierPoints = Bezier.getPoints(panel.pList)
                changeStateTo STATUS_BEZIER
                panel.repaint()
                return
            }
        }

        switch (painter.state) {

            case STATUS_BEGIN:
                panel.pStart = new Point(x, y)
                panel.pList.add(new Point(panel.pStart))
                log.info("select point at ${panel.pStart}")
                changeStateTo(STATUS_DRAG_LINE)
                break

            case STATUS_DRAG_LINE:
                panel.pList.add(new Point(panel.pEnd))
                panel.pStart = new Point(panel.pEnd)
                panel.repaint()
                break

            case STATUS_BEZIER:
                Point p0 = new Point(x, y)
                for (Point p : panel.pList)
                    if (p.closeTo(p0)) {
                        panel.pStart = p
                        changeStateTo(STATUS_MOVE_POINT)
                        break;
                    }
                break

            case STATUS_MOVE_POINT:
                changeStateTo(STATUS_BEZIER)
                break
        }
    }


    def void mouseMoved(MouseEvent e) {
        super.mouseMoved(e)
        int x = (int) e.x
        int y = (int) e.y

        if (x != panel.pCursor.x || y != panel.pCursor.y) {
            panel.pCursor.x = x
            panel.pCursor.y = y
            painter.updateState("Cursor at: ($x, $y)")
        }

        switch (painter.state) {

            case STATUS_DRAG_LINE:
                panel.pEnd = new Point(x, y)
                panel.repaint()
                break

            case STATUS_MOVE_POINT:
                panel.pStart.x = x
                panel.pStart.y = y
                panel.bezierPoints = Bezier.getPoints(panel.pList)
                System.gc()
                panel.repaint()
                break
        }
    }


}
