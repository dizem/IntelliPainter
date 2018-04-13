package org.dizem.intellipainter.ui.transformpainter

import javax.swing.JCheckBox

import org.dizem.intellipainter.graphics.Point

import org.dizem.intellipainter.algorithm.GraphicAlgorithm
import javax.swing.JComboBox
import java.awt.event.ActionEvent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JButton
import org.dizem.intellipainter.logger.Log
import java.awt.event.ActionListener

import javax.swing.Box
import javax.swing.JTextField
import java.awt.BorderLayout
import javax.swing.BorderFactory
import org.dizem.intellipainter.algorithm.graphictransform.GraphicMoving
import org.dizem.intellipainter.algorithm.graphictransform.GraphicRotating

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-19
 * Time: 21:19:35
 */

public class PainterToolsPanel extends JPanel implements ActionListener {

    TransformPainter painter
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
    def textBaseX = new JTextField(2)
    def textBaseY = new JTextField(2)
    def textEndX = new JTextField(2)
    def textEndY = new JTextField(2)
    def textAngle = new JTextField(2)
    def textList = [textStartX, textStartY, textEndX, textEndY, textAngle, textBaseX, textBaseY]
    def buttonRandom = new JButton("Random")
    def buttonDraw = new JButton("Transform")
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

        def listItem = new String[3]
        listItem[0] = 'Graphic Moving'
        listItem[1] = 'Graphic Zooming'
        listItem[2] = 'Graphic Rotating'

        algorithmList = new JComboBox(listItem)
        algorithmList.addActionListener this
        if (algorithm == GraphicAlgorithm.GRAPHIC_MOVING)
            algorithmList.setSelectedItem 'Graphic Moving'
        else if (algorithm == GraphicAlgorithm.GRAPHIC_ZOOMING)
            algorithmList.setSelectedItem 'Graphic Zooming'
        if (algorithm == GraphicAlgorithm.GRAPHIC_ROTATING)
            algorithmList.setSelectedItem 'Graphic Rotating'
        listPanel << algorithmList
        panelParent << listPanel

        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder('Input Panel'),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ))

        def hBox1 = Box.createHorizontalBox()
        def hBox2 = Box.createHorizontalBox()
        def hBox3 = Box.createHorizontalBox()
        def hBox4 = Box.createHorizontalBox()
        def hBox5 = Box.createHorizontalBox()
        def vBox = Box.createVerticalBox()
        hBox1 << new JLabel('From(')
        hBox1 << textStartX << new JLabel(',') << textStartY << new JLabel(')')
        hBox1 << new JLabel(' To(')
        hBox1 << textEndX << new JLabel(',') << textEndY << new JLabel(')')
        hBox2 << buttonRandom << new JLabel(' ') << buttonClearText << new JLabel(' ') << buttonDraw
        hBox3 << new JLabel(' ')
        hBox5 << new JLabel(' ')
        hBox4 << new JLabel('(Rotating/Zooming)Value ') << textAngle << new JLabel(' ')
        hBox4 << new JLabel('Base') << textBaseX << new JLabel(' ') << textBaseY
        vBox << hBox1 << hBox3 << hBox4 << hBox5 << hBox2
        inputPanel << vBox
        panelParent << inputPanel
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
            case buttonDraw:
                if (painter.algorithm == GraphicAlgorithm.GRAPHIC_MOVING) {
                    try {
                        Point p1 = new Point(Integer.parseInt(textStartX.text), Integer.parseInt(textStartY.text));
                        Point p2 = new Point(Integer.parseInt(textEndX.text), Integer.parseInt(textEndY.text));
                        log.info("Graphic Moving from $p1 to $p2}");
                        GraphicMoving.transform(painter.panel.polygon, p1, p2);
                        painter.panel.repaint();
                    } catch (Exception ex) {
                        log.info("Input Error");
                    }
                } else if (painter.algorithm == GraphicAlgorithm.GRAPHIC_ROTATING) {
                    try {
                        Point base;
                        try {
                            base = new Point(Integer.parseInt(textBaseX.text), Integer.parseInt(textBaseY.text));
                        } catch (Exception ex1) {
                            base = painter.panel.pBase;
                        }
                        double angle = Double.parseDouble(textAngle.text)
                        angle = angle / 180 * Math.PI;
                        log.info("Graphic Rotating [base=$base angle=$angle]");
                        GraphicRotating.transform(painter.panel.polygon, base, angle);
                        painter.panel.repaint();
                    } catch (Exception ex) {
                        log.info("Input Error");
                    }
                }

                break
            case buttonClearText:
                textList.each { text -> text.setText('') }
                log.info 'Input text cleared'
                break

            case buttonRandom:
                int x = 200
                int y = 200
                textStartX.setText "${rand.nextInt(x)}"
                textStartY.setText "${rand.nextInt(y)}"
                textEndX.setText "${rand.nextInt(x)}"
                textEndY.setText "${rand.nextInt(y)}"
                log.info 'Random point value setted'
                break

            case buttonClear:
                painter.state = 0;
                painter.panel.polygon.points.clear();
                painter.panel.repaint()
                painter.panel.pStart = null;
                painter.panel.pBase = null;
                painter.panel.pEnd = null;
                textList.each { text -> text.setText('') }
                log.info 'init'
                break;

            case algorithmList:
                String item = ((JComboBox) e.getSource()).getSelectedItem()
                if (!item)
                    return
                switch (item) {
                    case 'Graphic Moving':
                        painter.algorithm = GraphicAlgorithm.GRAPHIC_MOVING
                        break;
                    case 'Graphic Zooming':
                        painter.algorithm = GraphicAlgorithm.GRAPHIC_ZOOMING
                        break;
                    case 'Graphic Rotating':
                        painter.algorithm = GraphicAlgorithm.GRAPHIC_ROTATING
                        break;

                }
                painter.state = 0;
                painter.panel.polygon.points.clear();
                painter.panel.repaint()
                painter.panel.pStart = null;
                painter.panel.pBase = null;
                painter.panel.pEnd = null;
                textList.each { text -> text.setText('') }
                log.info 'init'
                log.info 'Algorithm changed to ' + item
                break
        }
    }

}
