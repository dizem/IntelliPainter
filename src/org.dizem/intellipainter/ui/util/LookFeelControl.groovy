package org.dizem.intellipainter.ui.util

import javax.swing.SwingUtilities
import javax.swing.UIManager

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-22
 * Time: 15:51:23
 */


public class LookFeelControl {

    static def FEELS = [
            'METAL'  : 'javax.swing.plaf.metal.MetalLookAndFeel',
            'MOTIF'  : 'com.sun.java.swing.plaf.motif.MotifLookAndFeel',
            'WINDOWS': 'com.sun.java.swing.plaf.windows.WindowsLookAndFeel',
            'NIMBUS' : 'org.jdesktop.swingx.plaf.nimbus.NimbusLookAndFeel'
    ]


    static def setLookAndFeel(frame, lookAndFeel) {
        def className = FEELS[lookAndFeel]
        if (className == null)
            throw new IllegalArgumentException()

        try {
            UIManager.setLookAndFeel(className)
            SwingUtilities.updateComponentTreeUI(frame)
        } catch (Exception e) {
            //println e
        }
    }
}
