package org.dizem.intellipainter.ui.bezierpainter

import java.awt.BorderLayout
import javax.swing.JInternalFrame
import org.dizem.intellipainter.logger.Log
import org.dizem.intellipainter.ui.panel.DesktopPanel
import org.dizem.intellipainter.algorithm.GraphicAlgorithm

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-24
 * Time: 17:21:24
 */
class BezierPainter extends JInternalFrame {

    int state = 0
    Log log
    DesktopPanel desktop
    BezierPainterPanel panel
    int ID
    String TITLE
    GraphicAlgorithm algorithm

    BezierPainter(String title, DesktopPanel p, GraphicAlgorithm a, int id) {
        super(title + " - Please select some points(End by click right button)", true, true, true, true)
        desktop = p
        log = p.log
        ID = id
        algorithm = a
        assert log != null
        panel = new BezierPainterPanel(this)
        TITLE = title
        setLayout(new BorderLayout())
        add(panel, BorderLayout.CENTER)
        add(new BezierPainterToolsPanel(this, a), BorderLayout.EAST)
    }

    void doDefaultCloseAction() {
        desktop.painterMap.remove(ID)
        log.info "Remove Frame(ID=$ID) from painterMap"
        super.doDefaultCloseAction()
    }

    void updateState(String state) {
        def panel = desktop.mainApp.statePanel
        panel.labelState.setText(state)
    }
}
