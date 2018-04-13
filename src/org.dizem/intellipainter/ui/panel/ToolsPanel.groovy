package org.dizem.intellipainter.ui.panel

import org.dizem.intellipainter.ui.MainApp

import javax.swing.JPanel
import javax.swing.BorderFactory
import java.awt.BorderLayout

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-22
 * Time: 22:07:31
 */
public class ToolsPanel extends JPanel {
    MainApp mainApp
    LoggerPanel loggerPanel
    SettingPanel settingPanel

    ToolsPanel(app) {
        mainApp = app
        loggerPanel = new LoggerPanel(app)
        settingPanel = new SettingPanel(app)
        loggerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder('Logger'),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ))
        settingPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder('Setting'),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ))
        setLayout new BorderLayout()
        add(settingPanel, BorderLayout.WEST)
        add(loggerPanel, BorderLayout.CENTER)

    }
}
