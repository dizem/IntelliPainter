package org.dizem.intellipainter.ui.linepainter

import java.awt.event.MouseListener
import java.awt.event.MouseEvent
import java.awt.event.MouseAdapter
import org.dizem.intellipainter.graphics.Point

import org.dizem.intellipainter.logger.Log

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-23
 * Time: 8:53:33
 */
public class PainterMouseHandler extends MouseAdapter implements MouseListener {
    PainterPanel painterPanel
    Log log

    PainterMouseHandler(panel) {
        painterPanel = panel
        log = panel.log
    }

    void mouseClicked(MouseEvent e) {

        int x = (int) (e.x / painterPanel.getRectLen() + 0.5)
        int y = (int) (e.y / painterPanel.getRectLen() + 0.5)

        log.info "${painterPanel.getName()}: Click At ($x, $y)"
        if (painterPanel.state == PaintState.NOTHING) {
            painterPanel.state = PaintState.POINT
            painterPanel.pStart = painterPanel.pEnd = new Point(x, y)
            painterPanel.repaint()

        } else if (painterPanel.state == PaintState.POINT) {
            if (x == painterPanel.pStart.x && y == painterPanel.pStart.y)
                return
            painterPanel.graphicPoints << painterPanel.points
            painterPanel.state = PaintState.NOTHING
            painterPanel.pEnd = new Point(x, y)
            log.info "${painterPanel.getName()}: From ${painterPanel.pStart} to ${painterPanel.pEnd} "
            def temp = painterPanel.getCreator()?.getPoints(painterPanel.pStart, painterPanel.pEnd)
            temp.each { p -> painterPanel.addPoints(p, painterPanel.graphicPoints) }
            log.info "${painterPanel.getName()}: Path: ${temp} "
            painterPanel.repaint()
        }
    }


    def void mouseMoved(MouseEvent e) {
        super.mouseMoved(e)
        def x = (int) (e.x / painterPanel.getRectLen() + 0.5)
        def y = (int) (e.y / painterPanel.getRectLen() + 0.5)

        if (x != painterPanel.pCursor.x || y != painterPanel.pCursor.y) {
            painterPanel.pCursor.x = x
            painterPanel.pCursor.y = y
            painterPanel.updateState("Cursor at: ($x, $y)")
        }
        if (painterPanel.state == PaintState.POINT) {
            painterPanel.pEnd = new Point(x, y)
//			def temp = painterPanel.getCreator().getPoints(painterPanel.pStart, new Point(x, y))
//			painterPanel.points.clear()
//			temp.each { p-> painterPanel.addPoints(p, painterPanel.points)}
//			painterPanel.repaint(
//					Math.min(painterPanel.pStart.x, painterPanel.pEnd.x),
//					Math.min(painterPanel.pStart.y, painterPanel.pEnd.y),
//					Math.max(painterPanel.pStart.x, painterPanel.pEnd.x),
//					Math.max(painterPanel.pStart.y, painterPanel.pEnd.y)
//			)
            painterPanel.repaint()
        }
    }
}
