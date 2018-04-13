package org.dizem.intellipainter.ui.panel

import javax.swing.JPanel
import org.dizem.intellipainter.ui.MainApp
import javax.swing.BorderFactory
import javax.swing.Box
import javax.swing.JLabel
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.JColorChooser
import java.awt.event.MouseListener
import java.awt.event.MouseEvent
import javax.swing.JSlider
import javax.swing.event.ChangeListener
import javax.swing.event.ChangeEvent
import org.dizem.intellipainter.logger.Log

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-28
 * Time: 20:21:09
 */
public class SettingPanel extends JPanel implements MouseListener, ChangeListener {


    MainApp mainAppI
    JPanel colorSettingPanel = new JPanel()
    Box boxColorRect = Box.createHorizontalBox()
    Box boxColorPoint = Box.createHorizontalBox()
    Box boxColorBack = Box.createHorizontalBox()
    JLabel colorRect = text('��')
    JLabel colorPoint = text('��')
    JLabel colorBack = text('��')
    JLabel textBack = text(' Back ')
    JLabel textRect = text('��Grid ')
    JLabel textPoint = text('Point ')
    JColorChooser colorChooser = new JColorChooser(MainApp.colorPoint)
    JSlider lineSizeSlider
    JPanel lineSizePanel = new JPanel()
    JSlider pixelSizeSlider
    JPanel pixelSizePanel = new JPanel()
    Box vBox1, vBox2
    Box hBox1, hBox2, hBox3
    int selectColorAt = 0;
    Log log

    public SettingPanel(app) {
        mainAppI = app
        log = app.log
        colorSettingPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder('Color'),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ))

        colorBack.setOpaque true
        colorBack.setBackground MainApp.colorBack
        colorBack.setBorder BorderFactory.createLineBorder(new Color(102, 153, 255), 2)
        colorPoint.setOpaque true
        colorPoint.setBackground MainApp.colorPoint
        colorPoint.setBorder BorderFactory.createLineBorder(new Color(102, 153, 255), 2)
        colorRect.setOpaque true
        colorRect.setBackground MainApp.colorRect
        colorRect.setBorder BorderFactory.createLineBorder(new Color(102, 153, 255), 2)

        colorBack.addMouseListener this
        colorPoint.addMouseListener this
        colorRect.addMouseListener this
        textBack.addMouseListener this
        textPoint.addMouseListener this
        textRect.addMouseListener this

        vBox1 = Box.createVerticalBox()
        hBox1 = Box.createHorizontalBox()
        hBox2 = Box.createHorizontalBox()
        hBox3 = Box.createHorizontalBox()

        boxColorBack << textBack << colorBack
        boxColorRect << textRect << colorRect
        boxColorPoint << textPoint << colorPoint
        vBox1 << boxColorBack << text(' ') << boxColorRect << text(' ') << boxColorPoint
        colorSettingPanel << vBox1

        colorSettingPanel << colorChooser

        lineSizePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder('Grid Size'),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ))
        lineSizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 11, 1)
        lineSizeSlider.setMajorTickSpacing 2
        lineSizeSlider.setMinorTickSpacing 1
        lineSizeSlider.setPaintTrack true
        lineSizeSlider.setPaintLabels true
        lineSizeSlider.addChangeListener this
        lineSizeSlider.setBounds(0, 0, 20, 40);
        lineSizePanel << lineSizeSlider


        pixelSizePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder('Pixcel Size'),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ))
        pixelSizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 5, 1)
        pixelSizeSlider.setMajorTickSpacing 1
        pixelSizeSlider.setMinorTickSpacing 1
        pixelSizeSlider.setPaintTrack true
        pixelSizeSlider.setPaintLabels true
        pixelSizeSlider.addChangeListener this
        pixelSizeSlider.setBounds(0, 0, 20, 40);
        pixelSizePanel << pixelSizeSlider


        vBox2 = Box.createVerticalBox()
        vBox2 << lineSizePanel << pixelSizePanel
        hBox1 = Box.createHorizontalBox()

        colorChooser.setPreviewPanel(new JLabel(''));
        colorChooser.getSelectionModel().addChangeListener this
        hBox1 << colorSettingPanel << colorChooser << vBox2


        updateColorSelection()

        setLayout new BorderLayout()
        add(hBox1, BorderLayout.WEST)
    }


    JLabel text(String s) {
        return new JLabel(s)
    }

    void updateColorSelection() {
        if (selectColorAt == 0) {
            boxColorBack.setBorder BorderFactory.createLineBorder(Color.BLACK, 1)
            boxColorRect.setBorder null
            boxColorPoint.setBorder null
        } else if (selectColorAt == 1) {
            boxColorBack.setBorder null
            boxColorRect.setBorder BorderFactory.createLineBorder(Color.BLACK, 1)
            boxColorPoint.setBorder null
        } else {
            boxColorBack.setBorder null
            boxColorRect.setBorder null
            boxColorPoint.setBorder BorderFactory.createLineBorder(Color.BLACK, 1)
        }
    }

    void mouseClicked(MouseEvent e) {
        switch (e.getSource()) {
            case colorBack:
            case textBack:
                selectColorAt = 0
                updateColorSelection()
//			Color oldColor = MainApp.colorBack
//			MainApp.colorBack = JColorChooser.showDialog(
//					mainAppI.frame, 'Choose Back Color', oldColor)
//			colorBack.setBackground MainApp.colorBack
//			DesktopPanel.painterMap.each { k,v->v?.panel?.repaint() }
//			log.info "Set background color from ${oldColor.toString()} to ${mainAppI.colorBack.toString()}"
                break

            case colorRect:
            case textRect:
                selectColorAt = 1
                updateColorSelection()
//			Color oldColor = MainApp.colorBack
//			MainApp.colorRect = JColorChooser.showDialog(
//					mainAppI.frame, 'Choose Line Color', oldColor)
//			colorRect.setBackground MainApp.colorRect
//			DesktopPanel.painterMap.each { k,v->v?.panel?.repaint() }
//			log.info "Set line color from ${oldColor.toString()} to ${MainApp.colorBack.toString()}"
                break

            case colorPoint:
            case textPoint:
                selectColorAt = 2
                updateColorSelection()
//			Color oldColor = MainApp.colorBack
//			MainApp.colorPoint = JColorChooser.showDialog(
//					mainAppI.frame, 'Choose Point Color', oldColor)
//			colorPoint.setBackground MainApp.colorPoint
//			DesktopPanel.painterMap.each { k,v->v?.panel?.repaint() }
//			log.info "Set point color from ${oldColor.toString()} to ${MainApp.colorBack.toString()}"
                break
        }
    }

    void stateChanged(ChangeEvent e) {
        //	log.info "get stateChange event from ${e.getSource()==colorChooser}"
        switch (e.getSource()) {
            case pixelSizeSlider:
                int value = ((JSlider) e.getSource()).getValue()
                MainApp.pixcelSize = value
                DesktopPanel.painterMap.each { k, v -> v?.panel?.repaint() }
                log.info "pixelSizeSlider changed to $value"

                break
            case lineSizeSlider:
                int value = ((JSlider) e.getSource()).getValue()
                MainApp.rectSize = value * 2
                DesktopPanel.painterMap.each { k, v -> v?.panel?.repaint() }
                log.info "gridSizeSlider changed to $value"
                break
            default:

                switch (selectColorAt) {
                    case 0:
                        Color oldColor = MainApp.colorBack
                        Color newColor = colorChooser.getColor()
                        if (newColor != MainApp.colorBack) {
                            MainApp.colorBack = newColor
                            colorBack.setBackground MainApp.colorBack
                            DesktopPanel.painterMap.each { k, v -> v?.panel?.repaint() }
                            log.info "Set background color from ${oldColor.toString()} to ${newColor.toString()}"
                        }
                        break
                    case 1:
                        Color oldColor = MainApp.colorRect
                        Color newColor = colorChooser.getColor()
                        if (newColor != MainApp.colorRect) {
                            MainApp.colorRect = newColor
                            colorRect.setBackground MainApp.colorRect
                            DesktopPanel.painterMap.each { k, v -> v?.panel?.repaint() }
                            log.info "Set grid color from ${oldColor.toString()} to ${newColor.toString()}"
                        }
                        break
                    case 2:
                        Color oldColor = MainApp.colorPoint
                        Color newColor = colorChooser.getColor()
                        if (newColor != MainApp.colorPoint) {
                            MainApp.colorPoint = newColor
                            colorPoint.setBackground MainApp.colorPoint
                            DesktopPanel.painterMap.each { k, v -> v?.panel?.repaint() }
                            log.info "Set point color from ${oldColor.toString()} to ${newColor.toString()}"
                        }
                        break
                }
                break
        }
    }

    void mousePressed(MouseEvent e) {

    }

    void mouseReleased(MouseEvent e) {

    }

    void mouseEntered(MouseEvent e) {

    }

    void mouseExited(MouseEvent e) {

    }


}
