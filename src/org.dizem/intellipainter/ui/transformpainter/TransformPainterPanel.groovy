package org.dizem.intellipainter.ui.transformpainter

import org.dizem.intellipainter.graphics.Polygon
import java.awt.Graphics
import org.dizem.intellipainter.graphics.Point
import java.awt.Color
import org.dizem.intellipainter.graphics.Line
import org.dizem.intellipainter.ui.MainApp
import javax.swing.JPanel

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-19
 * Time: 21:13:14
 */
class TransformPainterPanel extends JPanel {
    TransformPainter painter;
    Polygon polygon = new Polygon();
    Point pStart, pEnd, pBase, pCursor = new Point();
    Color colorWhite = new Color(255, 255, 255);
    Color colorBlack = new Color(0, 0, 0);
    Color colorBlue = new Color(0, 0, 255);
    int id;

    TransformPainterPanel(TransformPainter painter) {
        this.painter = painter;
        def handler = new TransformPainterMouseHandler(this)
        addMouseMotionListener handler
        addMouseListener handler
    }

    void paint(Graphics g) {
        g.setColor(MainApp.colorBack);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        List<Line> edges = polygon.getLines();
        for (Line edge : edges) {
            g.setColor(MainApp.colorPoint);
            g.drawLine(edge.a.x, edge.a.y, edge.b.x, edge.b.y);
            g.setColor(Color.BLUE);
            g.fillOval(edge.a.x - 5, edge.a.y - 5, 10, 10)
            g.fillOval(edge.b.x - 5, edge.b.y - 5, 10, 10)
        }

        if (painter.state == TransformPainterMouseHandler.STATUS_DRAW_EDGE && pEnd != null) {
            g.setColor(MainApp.colorPoint);
            g.drawLine(pStart.x, pStart.y, pEnd.x, pEnd.y);
        }

        if (painter.state == TransformPainterMouseHandler.STATUS_ZOOM && pEnd != null) {
            g.setColor(Color.GREEN);
            g.drawLine(pStart.x, pStart.y, pEnd.x, pEnd.y);
        }

        if (painter.state == TransformPainterMouseHandler.STATUS_DRAW_P1 && pEnd != null) {
            g.setColor(Color.GREEN);
            if (pEnd && pStart) g.drawLine(pStart.x, pStart.y, pEnd.x, pEnd.y);
            if (pBase && pStart) g.drawLine(pStart.x, pStart.y, pBase.x, pBase.y);
            if (pEnd && pBase) g.drawLine(pEnd.x, pEnd.y, pBase.x, pBase.y);
        }

        if (pBase) {
            g.setColor Color.BLUE
            g.fillOval(pBase.x - 5, pBase.y - 5, 10, 10)
            g.fillOval(pBase.x - 5, pBase.y - 5, 10, 10)
        }
    }

}