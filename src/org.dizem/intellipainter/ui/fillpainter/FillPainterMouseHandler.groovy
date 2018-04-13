package org.dizem.intellipainter.ui.fillpainter

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import org.dizem.intellipainter.graphics.Point
import org.dizem.intellipainter.logger.Log

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-23
 * Time: 8:53:33
 */
public class FillPainterMouseHandler extends MouseAdapter implements MouseListener {
    public static final int STATUS_BEGIN = 0;
    public static final int STATUS_DRAW_EDGE = 1;
    public static final int STATUS_CHOOSE_BASE = 2;
    public static final int STATUS_FILL = 3;
    FillPainter painter;
    FillPainterPanel painterPanel;
    Log log

    FillPainterMouseHandler(FillPainterPanel panel) {
        painterPanel = panel;
        this.painter = panel.painter;
        log = painter.log;
    }

    void mouseClicked(MouseEvent e) {

        int x = (int) e.x;
        int y = (int) e.y;

        log.info "Click At ($x, $y)"
        switch (painter.state) {
            case STATUS_BEGIN:
                painter.state = STATUS_DRAW_EDGE;
                painterPanel.pStart = new Point(x, y);
                painterPanel.polygon.addPoint(painterPanel.pStart);
                painterPanel.repaint();
                log.info("State = ${painter.state}");
                break;

            case STATUS_DRAW_EDGE:
                if (painterPanel.polygon.nEdge() > 0
                        && Math.abs(x - painterPanel.polygon.getFirst().x) < 10
                        && Math.abs(y - painterPanel.polygon.getFirst().y) < 10) {
                    log.info("A polygon(egde=${painterPanel.polygon.nEdge()} created)"
                            + "Please choose the seed point")
                    painterPanel.polygon.addPoint(painterPanel.polygon.getFirst());
                    painter.state = 2;
                    painterPanel.repaint();
                    log.info("State = ${painter.state}");
                    return;
                }
                if (x == painterPanel.pStart.x && y == painterPanel.pStart.y)
                    return;
                painterPanel.pStart = new Point(x, y);
                painterPanel.polygon.addPoint(painterPanel.pStart);
                painterPanel.repaint();
                log.info("State = ${painter.state}");
                break;

            case STATUS_CHOOSE_BASE:
                painterPanel.pStart = new Point(x, y);
                log.info("base = ${painterPanel.pStart}")
                painter.state = STATUS_FILL;
                painterPanel.repaint();
                log.info("State = ${painter.state}");
                break;

            case STATUS_FILL:
                painter.state = FillPainterMouseHandler.STATUS_BEGIN;
                painterPanel.polygon.points.clear();
                painterPanel.repaint();
        }


    }


    def void mouseMoved(MouseEvent e) {
        super.mouseMoved(e)
        int x = (int) e.x;
        int y = (int) e.y;

        if (x != painterPanel.pCursor.x || y != painterPanel.pCursor.y) {
            painterPanel.pCursor.x = x;
            painterPanel.pCursor.y = y;
            painter.updateState("Cursor at: ($x, $y)");
        }
        if (painter.state == STATUS_DRAW_EDGE) {
            painterPanel.pEnd = new Point(x, y)
            painterPanel.repaint()
        }
    }


}
