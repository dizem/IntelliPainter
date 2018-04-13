package org.dizem.intellipainter.ui.fillpainter

import javax.swing.JPanel
import java.awt.Graphics
import org.dizem.intellipainter.graphics.Point
import org.dizem.intellipainter.graphics.Polygon
import org.dizem.intellipainter.ui.MainApp
import org.dizem.intellipainter.graphics.Line
import java.awt.Color
import org.dizem.intellipainter.algorithm.fillpolygon.SeedFillPolygon
import org.dizem.intellipainter.algorithm.GraphicAlgorithm
import org.dizem.intellipainter.algorithm.fillpolygon.ScanLineFillPolygon

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-13
 * Time: 15:48:25
 */
class FillPainterPanel extends JPanel {
    FillPainter painter;
    Polygon polygon = new Polygon();
    Point pStart, pEnd, pCursor = new Point();
    Color colorWhite = new Color(255, 255, 255);
    Color colorBlack = new Color(0, 0, 0);


    FillPainterPanel(FillPainter painter) {
        this.painter = painter;
        def handler = new FillPainterMouseHandler(this)
        addMouseMotionListener handler
        addMouseListener handler
    }

    void paint(Graphics g) {
        g.setColor(MainApp.colorBack);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        List<Line> edges = polygon.getLines();
        g.setColor(MainApp.colorPoint);
        for (Line edge : edges) {
            g.drawLine(edge.a.x, edge.a.y, edge.b.x, edge.b.y);
        }

        if (painter.state == 1 && pEnd != null) {
            g.setColor(MainApp.colorPoint);
            g.drawLine(pStart.x, pStart.y, pEnd.x, pEnd.y);
        }

        if (painter.state == FillPainterMouseHandler.STATUS_FILL) {
            painter.log.info("Start Fill")
            if (painter.algorithm == GraphicAlgorithm.SEED_FILL)
                SeedFillPolygon.fill(g, MainApp.colorPoint, polygon, pStart, getWidth(), getHeight());
            else
                ScanLineFillPolygon.fill(g, MainApp.colorPoint, polygon, pStart, getWidth(), getHeight());
        }

        g.setColor(Color.BLUE);
        for (Line edge : edges) {
            g.fillOval(edge.a.x - 5, edge.a.y - 5, 10, 10)
            g.fillOval(edge.b.x - 5, edge.b.y - 5, 10, 10)
        }
    }
}
