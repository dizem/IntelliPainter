package org.dizem.intellipainter.ui.linepainter

import javax.swing.JPanel
import java.awt.Graphics

import org.dizem.intellipainter.graphics.Point

import org.dizem.intellipainter.logger.Log

import org.dizem.intellipainter.algorithm.drawline.BresenhamLine
import org.dizem.intellipainter.algorithm.drawline.MidLine
import org.dizem.intellipainter.ui.MainApp
import org.dizem.intellipainter.algorithm.drawcircle.BresenhamCircle
import org.dizem.intellipainter.algorithm.drawcircle.MidCircle
import org.dizem.intellipainter.algorithm.drawcircle.MPNCCircle
import org.dizem.intellipainter.algorithm.drawcircle.DDACircle

import org.dizem.intellipainter.algorithm.drawline.DDALine
import org.dizem.intellipainter.algorithm.GraphicAlgorithm

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-22
 * Time: 23:38:41
 */
class PainterPanel extends JPanel {

    def GraphicCreator = [:]
    def GraphicName = [:]

    Painter painter
    Set<Point> points = new HashSet<Point>()
    Set<Point> graphicPoints = new HashSet<Point>()


    def pCursor = new Point(0, 0)
    Log log
    PaintState state = PaintState.NOTHING
    GraphicAlgorithm algorithm

    Point pStart, pEnd
    boolean showLine = true


    int radius() {
        return MainApp.rectSize / 2 * (1 + MainApp.pixcelSize / 10)
    }

    PainterPanel(p, a) {
        //init GraphicCreator
        GraphicCreator[GraphicAlgorithm.DDA_LINE] = DDALine.class
        GraphicCreator[GraphicAlgorithm.MID_POINT_LINE] = MidLine.class
        GraphicCreator[GraphicAlgorithm.BRESENHAM_LINE] = BresenhamLine.class
        GraphicCreator[GraphicAlgorithm.BRESENHAM_CIRCLE] = BresenhamCircle.class
        GraphicCreator[GraphicAlgorithm.MID_POINT_CIRCLE] = MidCircle.class
        GraphicCreator[GraphicAlgorithm.MPNC_CILRCLE] = MPNCCircle.class
        GraphicCreator[GraphicAlgorithm.DDA_CIRCLE] = DDACircle.class
        //init GraphicName
        GraphicName[GraphicAlgorithm.DDA_LINE] = "DDA Line"
        GraphicName[GraphicAlgorithm.MID_POINT_LINE] = "Mid Line"
        GraphicName[GraphicAlgorithm.BRESENHAM_LINE] = "Bresenham Line"
        GraphicName[GraphicAlgorithm.BRESENHAM_CIRCLE] = "Bresenham Circle"
        GraphicName[GraphicAlgorithm.MID_POINT_CIRCLE] = "Mid Circle"
        GraphicName[GraphicAlgorithm.MPNC_CILRCLE] = "MPNC Circle"
        GraphicName[GraphicAlgorithm.DDA_CIRCLE] = 'DDA Circle'

        painter = p
        algorithm = a
        println a
        log = p.log
        def handler = new PainterMouseHandler(this)
        addMouseMotionListener handler
        addMouseListener handler

    }

    void paint(Graphics g) {
        int len = MainApp.rectSize
        int w = getWidth()
        int h = getHeight()
        g.setColor(MainApp.colorBack)
        g.fillRect(0, 0, w, h)
        g.setColor(MainApp.colorRect)
        //draw line
        if (showLine && len > 2) {
            for (int i = 0; i < w; i += len)
                g.drawLine(i, 0, i, h)
            for (int j = 0; j < h; j += len)
                g.drawLine(0, j, w, j)
        }
        if (state == PaintState.POINT)
            g.drawLine(
                    pStart.x * getRectLen() - radius(),
                    pStart.y * getRectLen() - radius(),
                    pEnd.x * getRectLen() - radius(),
                    pEnd.y * getRectLen() - radius()
            )
        g.setColor(MainApp.colorPoint)

        //points.each { p -> drawPoint(g, p.x, p.y) }

        graphicPoints.each { line -> line.each { p -> drawPoint(g, p.x, p.y) } }
    }

    static int[] dirX = [1, 0, -1, 0]
    static int[] dirY = [0, 1, 0, -1]


    void drawPoint(Graphics g, int x, int y) {
        if (MainApp.rectSize < 2)
            g.fillOval(x, y, 2, 2)
        else
            g.fillOval(
                    x * getRectLen() - radius(),
                    y * getRectLen() - radius(),
                    radius() * 2,
                    radius() * 2
            )
    }

    void addPoints(Point p, HashSet<Point> set) {
        int xMax = nRectsInX()
        int yMax = nRectsInY()

        int x = p.x
        int y = p.y
        if (MainApp.lineWidth == 1) {
            set << new Point(x, y)
            return
        }
        int halfSize = MainApp.lineWidth / 2;
        for (int i = x - halfSize; i < x + halfSize; ++i)
            for (int j = y - halfSize; j < y + halfSize; ++j) {
                if (i >= 0 && i < xMax && j >= 0 && j < yMax)
                    set << new Point(i, j)
            }
    }


    void updateState(state) {
        def panel = painter.desktop.mainApp.statePanel
        panel.labelState.setText state
    }

    def getRectLen() { MainApp.rectSize }

    String getName() { GraphicName[algorithm] }

    def getCreator() { GraphicCreator[algorithm] }

    def nRectsInX() { getWidth() / MainApp.rectSize }

    def nRectsInY() { getHeight() / MainApp.rectSize }
}
