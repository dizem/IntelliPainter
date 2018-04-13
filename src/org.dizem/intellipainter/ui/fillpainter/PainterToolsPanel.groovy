package org.dizem.intellipainter.ui.fillpainter

import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import org.dizem.intellipainter.algorithm.GraphicAlgorithm
import org.dizem.intellipainter.logger.Log

import javax.swing.*

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-19
 * Time: 21:19:35
 */

public class PainterToolsPanel extends JPanel implements ActionListener {

    FillPainter painter
    Log log
    def pixelSizeSlider
    def buttonClear = new JButton('Init')
    def buttonReplay = new JButton('Replay')
    def buttonExit = new JButton('Exit')
    def buttonPanel = new JPanel()
    def listPanel = new JPanel()

    def panelParent = Box.createVerticalBox()
    def buttonList = [buttonClear, buttonReplay, buttonExit]
    def algorithmList


    PainterToolsPanel(p, algorithm) {
        painter = p
        log = p.log

        setLayout new BorderLayout()

        listPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder('Algorithm Panel'),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ))

        def listItem = new String[2]
        listItem[0] = 'Scan Line Fill Polygon'
        listItem[1] = 'Seed Fill Polygon'

        algorithmList = new JComboBox(listItem)
        algorithmList.addActionListener this
        if (algorithm == GraphicAlgorithm.SCAN_LINE_FILL)
            algorithmList.setSelectedItem 'Scan Line Fill Polygon'
        else
            algorithmList.setSelectedItem 'Seed Fill Polygon'
        listPanel << algorithmList
        panelParent << listPanel
        add(panelParent, BorderLayout.NORTH)

        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder('Other Panel'),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ))
        buttonPanel << buttonClear
        buttonPanel << Box.createHorizontalStrut(5)
        buttonPanel << buttonExit
        add(buttonPanel, BorderLayout.SOUTH)
        buttonList.each { button -> button.addActionListener this }
    }


    JLabel empty(int width) { new JLabel(width) }
    def rand = new Random()
    int[] curSize = new int[2]

    void actionPerformed(ActionEvent e) {
        def p = painter.panel

        switch (e.getSource()) {
            case buttonExit:
                log.info "Close Form [Title = ${painter.TITLE}] "
                painter.doDefaultCloseAction()
                break

            case buttonClear:
                painter.state = 0;
                p.polygon.points.clear();
                p.repaint()
                p.pStart = null;
                p.pEnd = null;
                break;

            case algorithmList:
                String item = ((JComboBox) e.getSource()).getSelectedItem()
                if (!item)
                    return
                switch (item) {
                    case 'Scan Line Fill Polygon':
                        painter.algorithm = GraphicAlgorithm.SCAN_LINE_FILL
                        break;
                    case 'Seed Fill Polygon':
                        painter.algorithm = GraphicAlgorithm.SEED_FILL
                        break;
                }
                painter.state = 0;
                p.polygon.points.clear();
                p.repaint()
                p.pStart = null;
                p.pEnd = null;

                log.info 'init'
                log.info 'Algorithm changed to ' + item
                break
        }
    }

}
