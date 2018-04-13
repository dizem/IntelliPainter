package org.dizem.intellipainter.ui.clippainter

import java.awt.event.ActionListener
import javax.swing.JPanel
import org.dizem.intellipainter.logger.Log
import javax.swing.JButton
import javax.swing.Box
import java.awt.BorderLayout
import javax.swing.BorderFactory
import org.dizem.intellipainter.algorithm.GraphicAlgorithm
import java.awt.event.ActionEvent
import org.dizem.intellipainter.algorithm.clip.CSClip
import org.dizem.intellipainter.algorithm.clip.SHClip

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-24
 * Time: 11:15:56
 */
class ClipPainterToolsPanel extends JPanel implements ActionListener {

    ClipPainter painter
    Log log
    def pixelSizeSlider
    def buttonClear = new JButton('Init')
    def buttonExit = new JButton('Exit')
    def buttonClip = new JButton('Clip')
    def buttonPanel = new JPanel()
    def listPanel = new JPanel()

    def panelParent = Box.createVerticalBox()
    def buttonList = [buttonClear, buttonExit, buttonClip]
    def algorithmList


    ClipPainterToolsPanel(p, algorithm) {
        painter = p
        log = p.log

        setLayout new BorderLayout()
//
//		listPanel.setBorder(BorderFactory.createCompoundBorder(
//				BorderFactory.createTitledBorder('Algorithm Panel'),
//				BorderFactory.createEmptyBorder(5, 5, 5, 5)
//		))
//
//		def listItem = new String[2]
//		listItem[0] = 'Cohen-Sutherland Clip'
//		listItem[1] = 'Mid-Point Clip'
//
//		algorithmList = new JComboBox(listItem)
//		algorithmList.addActionListener this
//		if(algorithm == GraphicAlgorithm.CS_CLIP)
//			algorithmList.setSelectedItem 'Cohen-Sutherland Clip'
//		else
//			algorithmList.setSelectedItem 'Mid-Point Clip'
//		listPanel << algorithmList
//		panelParent << listPanel
//		add(panelParent, BorderLayout.NORTH)

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
                if (panel.rect && panel.lines.size() > 0) {
                    switch (painter.algorithm) {
                        case GraphicAlgorithm.CS_CLIP:
                            int id = 0;
                            panel.lines.each { line ->
                                CSClip.clip(line, panel.rect);
                                log.info("line[id=${id++}] cliped to $line")
                            }
                            panel.repaint();
                            break;
                        case GraphicAlgorithm.MID_POINT_CLIP:
                            int id = 0;
                            panel.lines.each { line ->
                                //MidPointClip.clip(line, panel.rect);
                                //todo:midPointA has bugs to be fix
                                CSClip.clip(line, panel.rect);
                                log.info("line[id=${id++}] cliped to $line")
                            }
                            panel.repaint();
                            break;
                        case GraphicAlgorithm.SH_CLIP:
                            SHClip.clip(panel.lines, panel.rect)
                            panel.repaint()
                            break;
                    }
                }
                break

            case buttonClear:
                painter.state = 0;
                painter.setTitle('Please drag a rect first')
                panel.lines.clear();
                panel.repaint()
                panel.pStart = null;
                panel.pEnd = null;
                break;

//		case algorithmList:
//			String item = ((JComboBox)e.getSource()).getSelectedItem()
//			if(!item)
//				return
//			switch(item) {
//			case 'Cohen-Sutherland Clip':
//				painter.algorithm = GraphicAlgorithm.CS_CLIP
//				break;
//			case 'Mid-Point Clip':
//				painter.algorithm = GraphicAlgorithm.MID_POINT_CLIP
//				break;
//			}
//			painter.state = 0;
//			painter.setTitle('Please drag a rect first')
//			panel.repaint()
//			panel.lines.clear();
//			panel.pStart = null;
//			panel.pEnd = null;
//
//			log.info 'init'
//			log.info 'Algorithm changed to ' + item
//			break
        }
    }

}
