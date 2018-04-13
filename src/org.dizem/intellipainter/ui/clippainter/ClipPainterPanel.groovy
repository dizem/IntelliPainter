package org.dizem.intellipainter.ui.clippainter;

import org.dizem.intellipainter.graphics.Line;
import org.dizem.intellipainter.ui.MainApp;

import javax.swing.*;

import org.dizem.intellipainter.graphics.Rect
import org.dizem.intellipainter.graphics.Point
import java.awt.Color
import java.awt.Graphics;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-24
 * Time: 11:15:30
 */
public class ClipPainterPanel extends JPanel {
    ClipPainter painter;
    Point pStart, pEnd, pCursor = new Point();
    Rect rect;
    ClipPainterMouseHandler handler;
    List<Line> lines = new ArrayList<Line>();

    ClipPainterPanel(ClipPainter painter) {
        this.painter = painter;
        handler = new ClipPainterMouseHandler(this)
        addMouseMotionListener handler
        addMouseListener handler
    }

    void paint(Graphics g) {
        g.setColor(MainApp.colorBack);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        switch (painter.state) {
            case handler.STATUS_DRAG_LINE:
                g.setColor(MainApp.colorPoint);
                g.drawLine(pStart.x, pStart.y, pEnd.x, pEnd.y)
                break;
        }
        if (painter.state != handler.STATUS_BEGIN) {
            g.setColor(Color.BLUE);
            drawRect(g);
        }
        g.setColor(MainApp.colorPoint);
        for (line in lines) {
            if (line)
                g.drawLine(line.a.x, line.a.y, line.b.x, line.b.y)
        }
        g.setColor(Color.BLUE);
        for (line in lines) {
            if (!line)
                continue;
            g.fillOval(line.a.x - 5, line.a.y - 5, 10, 10)
            g.fillOval(line.b.x - 5, line.b.y - 5, 10, 10)
        }
    }

    void drawRect(Graphics g) {
        Rect r = new Rect(rect);
        r.modify();
        g.drawRect(r.left, r.top, r.right - r.left, r.bottom - r.top);
    }
}