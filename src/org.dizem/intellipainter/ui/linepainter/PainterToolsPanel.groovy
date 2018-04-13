package org.dizem.intellipainter.ui.linepainter

import javax.swing.JPanel
import javax.swing.JButton
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.Box

import javax.swing.event.ChangeListener
import javax.swing.event.ChangeEvent
import javax.swing.JCheckBox
import javax.swing.JComboBox

import org.dizem.intellipainter.logger.Log
import javax.swing.JTextField
import javax.swing.JLabel

import org.dizem.intellipainter.graphics.Point
import javax.swing.JOptionPane
import javax.swing.JSlider
import org.dizem.intellipainter.ui.MainApp
import org.dizem.intellipainter.algorithm.GraphicAlgorithm

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-24
 * Time: 11:31:40
 */
public class PainterToolsPanel extends JPanel
        implements ActionListener, ChangeListener {

    Painter painter
    Log log
    def pixelSizeSlider
    def buttonClear = new JButton('Init')
    def buttonReplay = new JButton('Replay')
    def buttonExit = new JButton('Exit')
    def buttonPanel = new JPanel()
    def colorPanel = new JPanel()
    def sizePanel = new JPanel()
    def optionPanel = new JPanel()
    def checkShowLines = new JCheckBox('Show Lines')
    def listPanel = new JPanel()

    def panelParent = Box.createVerticalBox()
    def inputPanel = new JPanel()
    def textSize = new JLabel("Size Range:");
    def textStartX = new JTextField(2)
    def textStartY = new JTextField(2)
    def textEndX = new JTextField(2)
    def textEndY = new JTextField(2)
    def textList = [textStartX, textStartY, textEndX, textEndY]
    def buttonRandom = new JButton("Random")
    def buttonDraw = new JButton("Draw")
    def buttonClearText = new JButton("Clear")
    def buttonList = [buttonRandom, buttonDraw, buttonClearText, buttonClear, buttonReplay, buttonExit]
    def algorithmList


    PainterToolsPanel(p, algorithm) {
        painter = p
        log = p.log

        setLayout new BorderLayout()

        listPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder('Algorithm Panel'),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ))

        def listItem = new String[7]
        listItem[0] = 'DDA Line'
        listItem[1] = 'Mid Line'
        listItem[2] = 'Bresenham Line'
        listItem[3] = 'Mid Circle'
        listItem[4] = 'DDA Circle'
        listItem[5] = 'MPNC Circle'
        listItem[6] = 'Bresenham Circle'

        algorithmList = new JComboBox(listItem)
        algorithmList.addActionListener this
        algorithmList.setSelectedItem painter.panel.graphicName[algorithm]
        listPanel << algorithmList
        panelParent << listPanel

        //	listPanel << algorithmList
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder('Input Panel'),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ))

        def hBox1 = Box.createHorizontalBox()
        def hBox2 = Box.createHorizontalBox()
        def hBox3 = Box.createHorizontalBox()
        def vBox = Box.createVerticalBox()
        hBox1 << new JLabel('From(')
        hBox1 << textStartX << new JLabel(',') << textStartY << new JLabel(')')
        hBox1 << new JLabel(' To(')
        hBox1 << textEndX << new JLabel(',') << textEndY << new JLabel(')')
        hBox2 << buttonRandom << new JLabel(' ') << buttonClearText << new JLabel(' ') << buttonDraw
        hBox3 << new JLabel(' ')
        vBox << hBox1 << hBox3 << hBox2
        inputPanel << vBox
        panelParent << inputPanel
        add(panelParent, BorderLayout.NORTH)


        vBox = Box.createVerticalBox()
        optionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder('Line Width'),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ))
        checkShowLines.addChangeListener this
        checkShowLines.setSelected true

        pixelSizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 5, 1)
        pixelSizeSlider.setMajorTickSpacing 2
        pixelSizeSlider.setMinorTickSpacing 1
        pixelSizeSlider.setPaintTrack true
        pixelSizeSlider.setPaintLabels true
        pixelSizeSlider.addChangeListener this
        pixelSizeSlider.setBounds(0, 0, 20, 40);
        optionPanel << pixelSizeSlider
        add(optionPanel, BorderLayout.CENTER)


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
                p.state = PaintState.NOTHING
                p.points.clear()
                p.graphicPoints.clear()
                p.repaint()
                log.info "${painter.panel.getName()}: Board cleared"
                break

            case algorithmList:
                String item = ((JComboBox) e.getSource()).getSelectedItem()
                if (!item)
                    return
                if (item.contains('Line'))
                    painter.setTitle('Line')
                else if (item.contains('Circle'))
                    painter.setTitle('Circle')
                if (item.equals('DDA Line'))
                    p.algorithm = GraphicAlgorithm.DDA_LINE
                else if (item.equals('Mid Line'))
                    p.algorithm = GraphicAlgorithm.MID_POINT_LINE
                else if (item.equals('Bresenham Line'))
                    p.algorithm = GraphicAlgorithm.BRESENHAM_LINE
                else if (item.equals('Bresenham Circle'))
                    p.algorithm = GraphicAlgorithm.BRESENHAM_CIRCLE
                else if (item.equals('Mid Circle'))
                    p.algorithm = GraphicAlgorithm.MID_POINT_CIRCLE
                else if (item.equals('MPNC Circle'))
                    p.algorithm = GraphicAlgorithm.MPNC_CILRCLE
                else if (item.equals('DDA Circle'))
                    p.algorithm = GraphicAlgorithm.DDA_CIRCLE
                log.info 'Algorithm changed to ' + item
                break

            case buttonClearText:
                textList.each { text -> text.setText('') }
                log.info 'Input text cleared'
                break

            case buttonRandom:
                int x = painter.panel.nRectsInX()
                int y = painter.panel.nRectsInY()
                textStartX.setText "${rand.nextInt(x)}"
                textStartY.setText "${rand.nextInt(y)}"
                textEndX.setText "${rand.nextInt(x)}"
                textEndY.setText "${rand.nextInt(y)}"
                log.info 'Random point value setted'
                break

            case buttonDraw:
                int x = p.nRectsInX()
                int y = p.nRectsInY()
                int id = 0
                def numList = []
                try {
                    textList.each { text ->
                        int len = Integer.parseInt(text.getText())
                        if (len >= 0 && len < (id % 2 == 0 ? x : y))
                            numList << len
                        else
                            throw new Exception('out of range')
                    }
                } catch (Exception ex) {
                    def msg = "Point location params are error!\n" +
                            "max size[height=$x, width=$y]"
                    log.error msg
                    JOptionPane.showMessageDialog(painter, msg)
                    return
                }
                assert numList.size() == 4

                p.pStart = new Point(numList.get(0), numList.get(1))
                p.pEnd = new Point(numList.get(2), numList.get(3))
                log.info "${p.getName()}: Algorithm From ${p.pStart} to ${p.pEnd} "
                def temp = p.getCreator().getPoints(p.pStart, p.pEnd)
                temp.each { point -> p.addPoints(point, p.graphicPoints) }
                log.info "${p.getName()}: Path: $temp "
                p.repaint()
                break
        }
    }

    void stateChanged(ChangeEvent e) {

        switch (e.getSource()) {
            case pixelSizeSlider:
                int value = ((JSlider) e.getSource()).getValue()
                log.info "Slider changed to $value"
                MainApp.lineWidth = value
                break
            case checkShowLines:
                boolean flag = ((JCheckBox) e.getSource()).isSelected()
                painter.panel.showLine = flag
                painter.panel.repaint()
                break
        }
    }
}
