package org.dizem.intellipainter.ui.fillpainter

import java.awt.BorderLayout
import javax.swing.JInternalFrame
import org.dizem.intellipainter.logger.Log
import org.dizem.intellipainter.ui.panel.DesktopPanel
import org.dizem.intellipainter.algorithm.GraphicAlgorithm

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-22
 * Time: 23:34:03
 */
public class FillPainter extends JInternalFrame {

    int state = 0;
    Log log;
    DesktopPanel desktop;
    FillPainterPanel panel
    int ID;
    String TITLE;
    GraphicAlgorithm algorithm;

    FillPainter(String title, DesktopPanel p, GraphicAlgorithm a, int id) {
        super(title, true, true, true, true);
        desktop = p;
        log = p.log;
        ID = id;
        algorithm = a;
        assert log != null;
        panel = new FillPainterPanel(this);
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
