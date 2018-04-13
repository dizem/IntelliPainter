package org.dizem.intellipainter.ui

import javax.swing.JFrame
import org.dizem.intellipainter.ui.panel.PainterMenu
import java.awt.BorderLayout
import org.dizem.intellipainter.ui.panel.*
import javax.swing.JSplitPane
import javax.swing.JTabbedPane
import javax.swing.JPanel
import java.awt.event.ComponentEvent
import java.awt.event.ComponentAdapter
import java.awt.Toolkit

import org.dizem.intellipainter.logger.Log
import javax.swing.JTextArea
import java.awt.Color

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-22
 * Time: 10:38:04
 */
public class MainApp {
    static final def TITLE = 'IntelliPainter'
    static Color colorRect = new Color(102, 153, 255)
    static Color colorPoint = Color.RED
    static Color colorBack = Color.WHITE
    static int rectSize = 1
    static int lineWidth = 1
    static double pixcelSize = 1
    def frame

    public def logTextArea
    def log = new Log(this)

    def desktopPanel = new DesktopPanel(this)
    def treePanel = new TreePanel(this)
    def toolsPanel = new ToolsPanel(this)
    def mainPanel = new JPanel(new BorderLayout())
    def aboutPanel = new AboutPanel()
    def tabbedPane = new JTabbedPane()


    def statePanel = new StatePanel()
    def splitPaneRight
    def splitPane


    MainApp() {


        frame = new JFrame(TITLE)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        frame.setJMenuBar(new PainterMenu(this))
        frame.setLayout(new BorderLayout())
        initPanel()

        def screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(50, 50, (int) screenSize.width - 100, (int) screenSize.height - 100)
        frame.setVisible true

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int height = ((JFrame) e.getSource()).getHeight()
                splitPane.setDividerLocation height - 300
            }
        })

        logTextArea = new JTextArea(10, 400)
        log = new Log(this)
        log.info 'App start\n'
    }

    void initPanel() {
        mainPanel.add(statePanel, BorderLayout.SOUTH)

        splitPaneRight = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, treePanel, desktopPanel)
        splitPaneRight.setOneTouchExpandable true
        splitPaneRight.setDividerLocation 200

        splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT, splitPaneRight, toolsPanel)
        splitPane.setOneTouchExpandable true
        splitPane.setDividerLocation 400

        mainPanel.add(splitPane, BorderLayout.CENTER)
        tabbedPane.addTab('Main', mainPanel)
        tabbedPane.addTab('About', aboutPanel)

        frame.add(tabbedPane)
    }

    static void main(args) {
        new MainApp()
    }
}
