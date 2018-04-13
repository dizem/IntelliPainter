package org.dizem.intellipainter.ui.clippainter

import org.dizem.intellipainter.algorithm.GraphicAlgorithm
import org.dizem.intellipainter.ui.panel.DesktopPanel
import org.dizem.intellipainter.logger.Log
import javax.swing.JInternalFrame
import java.awt.BorderLayout

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-24
 * Time: 11:14:58
 */
class ClipPainter extends JInternalFrame {

    int state = 0;
    Log log;
    DesktopPanel desktop;
    ClipPainterPanel panel
    int ID;
    String TITLE;
    GraphicAlgorithm algorithm;

    ClipPainter(String title, DesktopPanel p, GraphicAlgorithm a, int id) {
        super(title + " - Please drag a rect first", true, true, true, true);
        desktop = p;
        log = p.log;
        ID = id;
        algorithm = a;
        assert log != null;
        panel = new ClipPainterPanel(this);
        TITLE = title;
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(new ClipPainterToolsPanel(this, a), BorderLayout.EAST);
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
