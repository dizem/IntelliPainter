package org.dizem.intellipainter.ui.clippainter

import java.awt.event.MouseListener
import java.awt.event.MouseAdapter
import org.dizem.intellipainter.logger.Log
import java.awt.event.MouseEvent
import org.dizem.intellipainter.graphics.Point
import org.dizem.intellipainter.graphics.Rect
import org.dizem.intellipainter.graphics.Line
import org.dizem.intellipainter.algorithm.GraphicAlgorithm

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-24
 * Time: 11:15:15
 */
class ClipPainterMouseHandler extends MouseAdapter implements MouseListener {

    public static final int STATUS_BEGIN = 0;
    public static final int STATUS_DRAG_RECT = 1;
    public static final int STATUS_DRAW_POINT = 2;
    public static final int STATUS_DRAG_LINE = 3;
    public static final int STATUS_CLIP = 4;
    ClipPainter painter;
    ClipPainterPanel panel;
    Log log

    ClipPainterMouseHandler(ClipPainterPanel panel) {
        this.panel = panel;
        this.painter = panel.painter;
        log = painter.log;
    }


    void changeStateTo(int state) {
        switch (state) {
            case STATUS_CLIP:
                painter.state = state;
                painter.setTitle("Clip - Click [clip] to do clip")
                log.info("state changed to STATUS_CLIP")
                break;
            case STATUS_DRAG_RECT:
                painter.state = state;
                painter.setTitle("Clip - Please drag a rect first")
                log.info("state changed to STATUS_DRAG_RECT")
                break;
            case STATUS_DRAW_POINT:
                painter.state = state;
                if (painter.algorithm != GraphicAlgorithm.SH_CLIP)
                    painter.setTitle("Clip - Please drag some lines")
                else
                    painter.setTitle("Clip - Please drag a polygon")
                log.info("state changed to STATUS_DRAW_POINT")
                break;
            case STATUS_DRAG_LINE:
                painter.state = state;
                log.info("state changed to STATUS_DRAG_LINE")
                break;
        }
    }


    void mouseClicked(MouseEvent e) {

        int x = (int) e.x;
        int y = (int) e.y;

        log.info "Click At ($x, $y)"
        switch (painter.state) {
            case STATUS_BEGIN:
                panel.pStart = new Point(x, y);
                panel.rect = new Rect(x, y, x, y);
                log.info("select point at ${panel.pStart}")
                changeStateTo(STATUS_DRAG_RECT)
                break;

            case STATUS_DRAG_RECT:
                panel.rect.update(x, y);
                panel.rect.modify()
                log.info("select ${panel.rect}")
                changeStateTo(STATUS_DRAW_POINT)
                break;

            case STATUS_DRAW_POINT:
//			if(painter.algorithm != GraphicAlgorithm.SH_CLIP) {
                panel.pStart = new Point(x, y)
                log.info("select pStart at ${panel.pStart}")

//			} else {
//				if(panel.lines.size() == 0)
//					panel.pStart = new Point(x, y)
//				else {
//					panel.pStart = new Point(panel.pEnd);
//					panel.pEnd = new Point(x, y);
//				}
//			}
                changeStateTo(STATUS_DRAG_LINE)
                break;

            case STATUS_DRAG_LINE:

                Line line = new Line(panel.pStart, panel.pEnd)
                log.info("select a line at ${line}")

                if (painter.algorithm != GraphicAlgorithm.SH_CLIP) {
                    panel.lines.add(line)
                    changeStateTo(STATUS_DRAW_POINT)
                } else {
                    if (panel.lines.size() > 0 && panel.pEnd.closeTo(panel.lines.get(0).a)) {
                        panel.pEnd = new Point(panel.lines.get(0).a)
                        panel.lines.add(new Line(panel.pStart, panel.pEnd))
                        changeStateTo(STATUS_CLIP)
                    } else {
                        panel.lines.add(line)
                        panel.pStart = new Point(panel.pEnd);
                    }

                }
                panel.repaint()
                break;
        }
    }


    def void mouseMoved(MouseEvent e) {
        super.mouseMoved(e)
        int x = (int) e.x;
        int y = (int) e.y;

        if (x != panel.pCursor.x || y != panel.pCursor.y) {
            panel.pCursor.x = x;
            panel.pCursor.y = y;
            painter.updateState("Cursor at: ($x, $y)");
        }
        Rect rect = panel.rect;
        switch (painter.state) {
            case STATUS_DRAG_RECT:
                rect.update(x, y)
                panel.repaint()
                break;

            case STATUS_DRAG_LINE:
                panel.pEnd = new Point(x, y)
                panel.repaint()
                break
        }
    }


}
