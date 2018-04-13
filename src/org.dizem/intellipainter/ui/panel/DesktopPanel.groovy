package org.dizem.intellipainter.ui.panel

import javax.swing.JDesktopPane
import org.dizem.intellipainter.ui.MainApp

import org.dizem.intellipainter.logger.Log
import org.dizem.intellipainter.graphics.Point
import javax.swing.JInternalFrame
import org.dizem.intellipainter.ui.fillpainter.FillPainter
import org.dizem.intellipainter.ui.linepainter.Painter
import org.dizem.intellipainter.ui.transformpainter.TransformPainter
import org.dizem.intellipainter.algorithm.GraphicAlgorithm
import org.dizem.intellipainter.ui.clippainter.ClipPainter
import org.dizem.intellipainter.ui.bezierpainter.BezierPainter

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-22
 * Time: 20:17:09
 */
public class DesktopPanel extends JDesktopPane {
    MainApp mainApp
    JInternalFrame internalFrame
    static def painterMap = [:]
    Log log

    DesktopPanel(app) {
        mainApp = app
        log = app.log
        setOpaque true
    }

    static def winPos = new Point(0, 0)
    static int times = 0
    static int ID = 0

    void updatePosition() {
        int h = this.height - 100
        if (winPos.y + h > height) {
            ++times
            winPos.x = times * 30
            winPos.y = 0
            return
        }
        winPos.x += 30
        winPos.y += 30
        return
    }


    void createFrame(String title, GraphicAlgorithm algorithm) {
        log.info title
        if (title.contains("Fill")) {
            internalFrame = new FillPainter(title, this, algorithm, ID);
        } else if (title.contains("Transform")) {
            internalFrame = new TransformPainter(title, this, algorithm, ID);
        } else if (title.contains("Clip")) {
            internalFrame = new ClipPainter(title, this, algorithm, ID);
        } else if (title.contains("Bezier")) {
            internalFrame = new BezierPainter(title, this, algorithm, ID);
        } else {
            internalFrame = new Painter(title, this, algorithm, ID);
        }
        int h = this.height - 45
        internalFrame.setBounds(winPos.x, winPos.y, (int) (h * 5 / 3), h)
        internalFrame.setVisible true
        internalFrame.setFocusable true
        add(internalFrame, 0)
        log.info "Create Frame [Title = $title]"
        painterMap[ID++] = internalFrame
        log.info "Add Frame(id=$ID) to painterMap"
        updatePosition()
    }
}
