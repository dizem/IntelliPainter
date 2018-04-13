package org.dizem.intellipainter.ui.panel

import javax.swing.JScrollPane
import org.dizem.intellipainter.ui.MainApp
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.ScrollPaneLayout
import java.awt.Point
import javax.swing.event.TreeSelectionListener
import javax.swing.event.TreeSelectionEvent
import org.dizem.intellipainter.algorithm.GraphicAlgorithm
import org.dizem.intellipainter.logger.Log

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-22
 * Time: 21:25:41
 */
public class TreePanel extends JScrollPane implements TreeSelectionListener {
    MainApp mainApp
    def top = new DefaultMutableTreeNode(MainApp.TITLE)
    def tree = new JTree(top)
    Log log

    public TreePanel(MainApp app) {
        log = app.log
        setLayout(new ScrollPaneLayout.UIResource())
        setVerticalScrollBarPolicy 20
        setHorizontalScrollBarPolicy 30
        setViewport createViewport()
        setVerticalScrollBar createVerticalScrollBar()
        setHorizontalScrollBar createHorizontalScrollBar()
        setViewportView tree
        setOpaque true

        updateUI()
        if (!this.getComponentOrientation().isLeftToRight())
            viewport.setViewPosition(new Point(Integer.MAX_VALUE, 0))

        addNode(top)
        tree.expandRow 0
        tree.expandRow 1
        tree.expandRow 5
        tree.expandRow 10
        tree.expandRow 13
        tree.addTreeSelectionListener this
        mainApp = app
    }

    void addNode(parent) {
        def categroy1 = new DefaultMutableTreeNode('Draw Line')
        categroy1 << new DefaultMutableTreeNode('DDA Line')
        categroy1 << new DefaultMutableTreeNode('Mid Point Line')
        categroy1 << new DefaultMutableTreeNode('Bresenham Line')

        def categroy2 = new DefaultMutableTreeNode('Draw Circle')
        categroy2 << new DefaultMutableTreeNode('DDA Circle')
        categroy2 << new DefaultMutableTreeNode('Bresenham Circle')
        categroy2 << new DefaultMutableTreeNode('MPNC Circle')
        categroy2 << new DefaultMutableTreeNode('Mid Point Circle')


        def categroy3 = new DefaultMutableTreeNode('Fill Polygon')
        categroy3 << new DefaultMutableTreeNode('ScanLineFillPolygon')
        categroy3 << new DefaultMutableTreeNode('SeedFillPolygon')

        def categroy4 = new DefaultMutableTreeNode('Transform Graphic')
        categroy4 << new DefaultMutableTreeNode('Graphic Moving')
        categroy4 << new DefaultMutableTreeNode('Graphic Zooming')
        categroy4 << new DefaultMutableTreeNode('Graphic Rotating')

        def categroy5 = new DefaultMutableTreeNode('Clip')
        categroy5 << new DefaultMutableTreeNode('Cohen-Sutherland Clip')
        categroy5 << new DefaultMutableTreeNode('Mid-Point Clip')
        categroy5 << new DefaultMutableTreeNode('Sutherland-Hodgman Clip')

        def categroy6 = new DefaultMutableTreeNode('Bezier ')
        categroy6 << new DefaultMutableTreeNode('Bezier')
        parent << categroy1 << categroy2 << categroy3 << categroy5 << categroy4 << categroy6
    }

    void valueChanged(TreeSelectionEvent e) {
        def node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent()
        if (node == null)
            return

        DesktopPanel panel = mainApp.desktopPanel
        log.info "Tree Node: ${node.toString()}"
        switch (node.toString()) {
            case 'Sutherland-Hodgman Clip':
                panel.createFrame('Clip', GraphicAlgorithm.SH_CLIP)
                break;

            case 'Bezier':
                panel.createFrame('Bezier', GraphicAlgorithm.BEZIER)
                break;

            case 'Cohen-Sutherland Clip':
                panel.createFrame('Clip', GraphicAlgorithm.CS_CLIP)
                break;
            case 'Mid-Point Clip':
                panel.createFrame('Clip', GraphicAlgorithm.MID_POINT_CLIP)
                break;
            case 'DDA Line':
                panel.createFrame('Line', GraphicAlgorithm.DDA_LINE)
                break
            case 'Mid Point Line':
                panel.createFrame('Line', GraphicAlgorithm.MID_POINT_LINE)
                break
            case 'Bresenham Line':
                panel.createFrame('Line', GraphicAlgorithm.BRESENHAM_LINE)
                break
            case 'Bresenham Circle':
                panel.createFrame('Circle', GraphicAlgorithm.BRESENHAM_CIRCLE)
                break
            case 'Mid Point Circle':
                panel.createFrame('Circle', GraphicAlgorithm.MID_POINT_CIRCLE)
                break
            case 'MPNC Circle':
                panel.createFrame('Circle', GraphicAlgorithm.MPNC_CILRCLE)
                break
            case 'DDA Circle':
                panel.createFrame('Circle', GraphicAlgorithm.DDA_CIRCLE)
                break

            case 'ScanLineFillPolygon':
                panel.createFrame('FillPloygon', GraphicAlgorithm.SCAN_LINE_FILL)
                break

            case 'SeedFillPolygon':
                panel.createFrame('FillPloygon', GraphicAlgorithm.SEED_FILL)
                break;

            case 'Graphic Moving':
                panel.createFrame('Graphic Transform', GraphicAlgorithm.GRAPHIC_MOVING)
                break;

            case 'Graphic Zooming':
                panel.createFrame('Graphic Transform', GraphicAlgorithm.GRAPHIC_ZOOMING)
                break;

            case 'Graphic Rotating':
                panel.createFrame('Graphic Transform', GraphicAlgorithm.GRAPHIC_ROTATING)
                break;
        }

    }
}
