package org.dizem.intellipainter.ui.panel


import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JMenu
import org.dizem.intellipainter.ui.MainApp
import java.awt.event.ActionListener
import java.awt.event.ActionEvent

import org.dizem.intellipainter.algorithm.GraphicAlgorithm

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-22
 * Time: 11:10:36
 */
class PainterMenu extends JMenuBar implements ActionListener {
    MainApp mainApp

    def menuFile = new JMenu("Options");
    def itemNew = new JMenuItem("New")
    def itemOpen = new JMenuItem("Open")
    def itemSave = new JMenuItem("Save")
    def itemSaveAs = new JMenuItem("Save as")
    def itemExit = new JMenuItem("Exit")
    def itemAbout = new JMenuItem("About")

    def menuSetting = new JMenu("Setting")
    def menuLookAndFeel = new JMenu("Theme")
    def itemWindows = new JMenuItem("Windows")
    def itemMotif = new JMenuItem("Motif")
    def itemMetal = new JMenuItem("Metal")
    def itemNimBus = new JMenuItem("Nimbus")

    def menuLine = new JMenu("DrawLine")
    def itemDDALine = new JMenuItem('DDA Line')
    def itemBLine = new JMenuItem('Bresenham Line')
    def itemMidLine = new JMenuItem('Mid Point Line')

    def menuCircle = new JMenu("DrawCircle")
    def itemDDACircle = new JMenuItem('DDA Circle')
    def itemBCircle = new JMenuItem('Bresenham Circle')
    def itemMidCircle = new JMenuItem('Mid Point Circle')
    def MPNCCircle = new JMenuItem('MPNC Circle')

    def menuTransform = new JMenu("Transform")
    def itemMoving = new JMenuItem('Graphic Moving')
    def itemZooming = new JMenuItem('Graphic Zooming')
    def itemRotating = new JMenuItem('Graphic Rotating')
    def itemList = []

    def menuFill = new JMenu("FillPolygon")
    def itemScanLineFill = new JMenuItem('Scan Line Fill Polygon')
    def itemSeedFill = new JMenuItem('Seed Fill Polygon')

    def menuBezier = new JMenu("Bezier")
    def itemBezier = new JMenuItem("Bezier")

    def menuClip = new JMenu("Clip")
    def itemCSClip = new JMenuItem('Cohen-Sutherland Clip')
    def itemMidClip = new JMenuItem('Mid-Point Clip')
    def itemSHClip = new JMenuItem('Sutherland-Hodgman Clip')

    PainterMenu(app) {
        mainApp = app

        menuFile << itemAbout << itemExit
        menuLine << itemDDALine << itemBLine << itemMidLine
        menuCircle << itemDDACircle << itemBCircle << itemMidCircle << MPNCCircle
        menuTransform << itemMoving << itemZooming << itemRotating
        menuFill << itemScanLineFill << itemSeedFill
        menuClip << itemCSClip << itemMidClip << itemSHClip
        menuBezier << itemBezier
        this << menuFile << menuLine << menuCircle << menuFill << menuClip << menuTransform << menuBezier

        itemList << itemAbout << itemExit << itemDDALine << itemBLine << itemMidLine <<
                itemDDACircle << itemBCircle << itemMidCircle << MPNCCircle <<
                itemMoving << itemZooming << itemRotating << itemScanLineFill <<
                itemSeedFill << menuBezier << itemMidClip << itemSHClip << itemCSClip << itemBezier

        itemList*.addActionListener(this)
    }


    void actionPerformed(ActionEvent e) {
        DesktopPanel panel = mainApp.desktopPanel
        switch (e.getSource()) {
            case itemAbout:
                mainApp.tabbedPane.setSelectedIndex 1
                break;
            case itemSHClip:
                panel.createFrame('Clip', GraphicAlgorithm.SH_CLIP)
                break
            case itemCSClip:
                panel.createFrame('Clip', GraphicAlgorithm.CS_CLIP)
                break
            case itemMidClip:
                panel.createFrame('Clip', GraphicAlgorithm.MID_POINT_CLIP)
                break
            case itemBezier:
                panel.createFrame('Bezier', GraphicAlgorithm.BEZIER)
                break
            case itemDDALine:
                panel.createFrame('Line', GraphicAlgorithm.DDA_LINE)
                break
            case itemMidLine:
                panel.createFrame('Line', GraphicAlgorithm.MID_POINT_LINE)
                break
            case itemBLine:
                panel.createFrame('Line', GraphicAlgorithm.BRESENHAM_LINE)
                break
            case itemBCircle:
                panel.createFrame('Circle', GraphicAlgorithm.BRESENHAM_CIRCLE)
                break
            case itemMidCircle:
                panel.createFrame('Circle', GraphicAlgorithm.MID_POINT_CIRCLE)
                break
            case MPNCCircle:
                panel.createFrame('Circle', GraphicAlgorithm.MPNC_CILRCLE)
                break
            case itemDDACircle:
                panel.createFrame('Circle', GraphicAlgorithm.DDA_CIRCLE)
                break

            case itemScanLineFill:
                panel.createFrame('FillPloygon', GraphicAlgorithm.SCAN_LINE_FILL)
                break

            case itemSeedFill:
                panel.createFrame('FillPloygon', GraphicAlgorithm.SEED_FILL)
                break;


            case itemMoving:
                panel.createFrame('Graphic Transform', GraphicAlgorithm.GRAPHIC_MOVING)
                break;

            case itemZooming:
                panel.createFrame('Graphic Transform', GraphicAlgorithm.GRAPHIC_ZOOMING)
                break;

            case itemRotating:
                panel.createFrame('Graphic Transform', GraphicAlgorithm.GRAPHIC_ROTATING)
                break;
        }
    }
}
