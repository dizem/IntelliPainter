package org.dizem.intellipainter.ui.transformpainter

import java.awt.event.MouseListener
import java.awt.event.MouseAdapter
import org.dizem.intellipainter.logger.Log
import java.awt.event.MouseEvent
import org.dizem.intellipainter.graphics.Point
import org.dizem.intellipainter.algorithm.graphictransform.GraphicMoving
import org.dizem.intellipainter.algorithm.GraphicAlgorithm
import org.dizem.intellipainter.algorithm.graphictransform.GraphicRotating
import org.dizem.intellipainter.algorithm.graphictransform.GraphicZooming

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-19
 * Time: 21:13:00
 */

public class TransformPainterMouseHandler extends MouseAdapter implements MouseListener {
    public static final int STATUS_BEGIN = 0;
    public static final int STATUS_DRAW_EDGE = 1;
    public static final int STATUS_DRAW_END = 2;
    public static final int STATUS_DRAW_P1 = 3;
    public static final int STATUS_SELECT_BASE = 4;
    public static final int STATUS_ZOOM = 5;
    TransformPainter painter;
    TransformPainterPanel painterPanel;
    Log log

    TransformPainterMouseHandler(TransformPainterPanel panel) {
        painterPanel = panel;
        this.painter = panel.painter;
        log = painter.log;
    }

    void mouseClicked(MouseEvent e) {

        int x = (int) e.x;
        int y = (int) e.y;
        log.info("current state = ${painter.state}")
        log.info "Click At ($x, $y)"
        switch (painter.state) {
            case STATUS_BEGIN:
                painter.state = STATUS_DRAW_EDGE;
                painterPanel.pStart = new Point(x, y);
                painterPanel.polygon.addPoint(painterPanel.pStart);
                painterPanel.repaint();
                break;
            case STATUS_DRAW_EDGE:
                if (painterPanel.polygon.nEdge() > 0
                        && Math.abs(x - painterPanel.polygon.getFirst().x) < 10
                        && Math.abs(y - painterPanel.polygon.getFirst().y) < 10) {
                    log.info("A polygon(egde=${painterPanel.polygon.nEdge()} created)"
                            + "Please choose the seed point")
                    painterPanel.polygon.addPoint(painterPanel.polygon.getFirst());
                    if (painter.algorithm == GraphicAlgorithm.GRAPHIC_MOVING)
                        painter.state = STATUS_DRAW_END;
                    else
                        painter.state = STATUS_SELECT_BASE;
                    painterPanel.pEnd = null;
                    painterPanel.repaint();
                    return;
                }
                if (x == painterPanel.pStart.x && y == painterPanel.pStart.y)
                    return;
                painterPanel.pStart = new Point(x, y);
                painterPanel.polygon.addPoint(painterPanel.pStart);
                painterPanel.repaint();
                break;

            case STATUS_DRAW_END:
                painterPanel.pStart = new Point(x, y);
                if (painter.algorithm == GraphicAlgorithm.GRAPHIC_ROTATING) {
                    painter.state = STATUS_DRAW_P1;
                    painterPanel.repaint();
                } else if (painter.algorithm == GraphicAlgorithm.GRAPHIC_MOVING) {
                    painter.state = STATUS_DRAW_P1;
                    painterPanel.repaint();
                } else {
                    boolean flag = false;
                    List<Point> p = painterPanel.polygon.points;
                    for (int i = 0; i < p.size(); ++i)
                        if (p.get(i).closeTo(painterPanel.pStart)) {
                            painterPanel.pStart = new Point(p.get(i));
                            flag = true;
                            painterPanel.id = i;
                            break;
                        }
                    if (flag)
                        painter.state = STATUS_ZOOM;
                }

                break;

            case STATUS_SELECT_BASE:
                painterPanel.pBase = new Point(x, y);
                List<Point> points = painterPanel.polygon.points;
                log.info("select base point at $painterPanel.pBase")
                painter.state = STATUS_DRAW_END;
                painterPanel.repaint();
                break;

            case STATUS_DRAW_P1:
                painterPanel.pEnd = new Point(x, y);
                if (painter.algorithm == GraphicAlgorithm.GRAPHIC_MOVING) {
                    painter.state = STATUS_DRAW_END;
                    GraphicMoving.transform(painterPanel.polygon, painterPanel.pStart, painterPanel.pEnd);
                    log.info("Graphic Moving from ${painterPanel?.pStart} to ${painterPanel?.pEnd}");
                    painterPanel.pEnd = null;
                    painterPanel.repaint();
                } else if (painter.algorithm == GraphicAlgorithm.GRAPHIC_ROTATING) {
                    painter.state = STATUS_SELECT_BASE;
                    GraphicRotating.transform(painterPanel.polygon,
                            painterPanel.pBase, painterPanel.pStart, painterPanel.pEnd);
                    painterPanel.pBase = null;
                    painterPanel.repaint();
                }

                break;

            case STATUS_ZOOM:
                painter.state = STATUS_SELECT_BASE;
                GraphicZooming.transform(painterPanel.polygon, painterPanel.pBase,
                        x, painterPanel.id);
                log.info("Zoom [base=${painterPanel.pBase}")
                painterPanel.repaint();
                painterPanel.pBase = null;
                painterPanel.repaint();
                break;
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
        if (painter.state == STATUS_DRAW_EDGE || painter.state == STATUS_DRAW_P1) {
            painterPanel.pEnd = new Point(x, y)
            painterPanel.repaint()
        }

        if (painter.state == STATUS_ZOOM) {
            painterPanel.pEnd = new Point(x, y)
            painterPanel.repaint()
//			GraphicZooming.transform(painterPanel.polygon, painterPanel.pBase,
//					x, painterPanel.id);
//			log.info("Zoom [base=${painterPanel.pBase}")
//			painterPanel.repaint();
        }
    }


}
