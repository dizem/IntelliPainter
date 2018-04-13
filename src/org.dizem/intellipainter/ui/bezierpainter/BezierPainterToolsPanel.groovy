package org.dizem.intellipainter.ui.bezierpainter

import javax.swing.JPanel
import java.awt.event.ActionListener
import javax.swing.BorderFactory
import org.dizem.intellipainter.logger.Log
import javax.swing.JButton
import javax.swing.Box
import java.awt.BorderLayout

import java.awt.event.ActionEvent
import org.dizem.intellipainter.algorithm.bezier.Bezier
import org.dizem.intellipainter.graphics.Point

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-24
 * Time: 17:22:35
 */
class BezierPainterToolsPanel extends JPanel implements ActionListener {

    BezierPainter painter
    Log log
    def pixelSizeSlider
    def buttonClear = new JButton('Init')
    def buttonReplay = new JButton('Replay')
    def buttonExit = new JButton('Exit')
    def buttonClip = new JButton('Bazier')
    def buttonPanel = new JPanel()
    def listPanel = new JPanel()

    def panelParent = Box.createVerticalBox()
    def buttonList = [buttonClear, buttonReplay, buttonExit, buttonClip]
    def algorithmList


    BezierPainterToolsPanel(p, algorithm) {
        painter = p
        log = p.log

        setLayout new BorderLayout()

        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder('Controls'),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ))
        def box = Box.createVerticalBox()
        box << buttonClip
        box << Box.createVerticalStrut(5)
        box << buttonClear
        box << Box.createVerticalStrut(5)
        box << buttonExit
        buttonPanel << box
        add(buttonPanel, BorderLayout.SOUTH)
        buttonList.each { button -> button.addActionListener this }
    }


    void actionPerformed(ActionEvent e) {
        def panel = painter.panel

        switch (e.getSource()) {
            case buttonExit:
                log.info "Close Form [Title = ${painter.TITLE}] "
                painter.doDefaultCloseAction()
                break

            case buttonClip:
                panel.bezierPoints = Bezier.getPoints(panel.pList)
                painter.state = BezierPainterMouseHandler.STATUS_BEZIER
                painter.setTitle('Bezier - Select a point to move')
                panel.repaint()
                break

            case buttonClear:
                painter.state = 0;
                panel.pStart = panel.pEnd = null
                panel.pList = new ArrayList<Point>()
                panel.bezierPoints = null
                painter.setTitle('Bezier - Please select some points(End by click right button)')
                System.gc()
                panel.repaint()
                break
        }
    }
}