package org.dizem.intellipainter.ui.transformpainter

import org.dizem.intellipainter.ui.panel.DesktopPanel
import org.dizem.intellipainter.logger.Log
import java.awt.BorderLayout
import javax.swing.JInternalFrame
import org.dizem.intellipainter.algorithm.GraphicAlgorithm

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-19
 * Time: 21:12:45
 */
class TransformPainter extends JInternalFrame {
    int state = 0;
    Log log;
    DesktopPanel desktop;
    TransformPainterPanel panel
    int ID;
    String TITLE;
    GraphicAlgorithm algorithm;

    TransformPainter(String title, DesktopPanel p, GraphicAlgorithm a, int id) {
        super(title, true, true, true, true);
        desktop = p;
        log = p.log;
        ID = id;
        algorithm = a;
        assert log != null;
        panel = new TransformPainterPanel(this);
        TITLE = title;
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(new PainterToolsPanel(this, a), BorderLayout.EAST);
    }

    void doDefaultCloseAction() {
        desktop.painterMap.remove(ID);
        log.info "Remove Frame(ID=$ID) from painterMap";
        super.doDefaultCloseAction();
    }

    void updateState(String state) {
        def panel = desktop.mainApp.statePanel;
        panel.labelState.setText(state);
    }
}