package org.dizem.intellipainter.ui.linepainter

import javax.swing.JInternalFrame
import java.awt.BorderLayout
import org.dizem.intellipainter.ui.panel.DesktopPanel

import org.dizem.intellipainter.logger.Log

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-22
 * Time: 23:34:03
 */
public class Painter extends JInternalFrame {


    Log log
    DesktopPanel desktop
    public PainterPanel panel
    PainterToolsPanel toolsPanel
    int ID
    String TITLE

    Painter(t, p, algorithm, int id) {
        super(t, true, true, true, true)
        desktop = p
        log = p.log
        ID = id
        assert log != null
        panel = new PainterPanel(this, algorithm)
        toolsPanel = new PainterToolsPanel(this, algorithm)
        TITLE = t
        setLayout(new BorderLayout())
        add(panel, BorderLayout.CENTER)
        add(toolsPanel, BorderLayout.EAST)
    }

    void doDefaultCloseAction() {
        desktop.painterMap.remove(ID)
        log.info "Remove Frame(ID=$ID) from painterMap"
        super.doDefaultCloseAction()
    }
}
