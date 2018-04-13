package org.dizem.intellipainter.ui.panel

import javax.swing.JPanel
import java.awt.BorderLayout
import javax.swing.JTextArea
import javax.swing.JScrollPane
import org.dizem.intellipainter.ui.MainApp
import javax.swing.JMenuItem
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import javax.swing.JPopupMenu

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-22
 * Time: 22:28:38
 */
public class LoggerPanel extends JPanel implements ActionListener {

    JTextArea textArea
    JScrollPane textPane
    MainApp mainApp
    def itemClear = new JMenuItem('Clear All')
    def popupMenu = new JPopupMenu()

    LoggerPanel(app) {
        mainApp = app
        //mainApp.logTextArea = new JTextArea()
        assert mainApp.logTextArea
        textArea = mainApp.logTextArea
        textArea.setText 'App Start'
        popupMenu.add itemClear
        textArea.add popupMenu
        itemClear.addActionListener this
        textPane = new JScrollPane(textArea)
        setLayout(new BorderLayout())
        add(textPane, BorderLayout.CENTER)
    }

    void actionPerformed(ActionEvent e) {
        switch (e.getSource()) {
            case itemClear:
                textArea.setText(' ')
                break;
        }
    }
}
