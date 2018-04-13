package org.dizem.intellipainter.ui.panel

import javax.swing.JPanel
import java.awt.BorderLayout
import javax.swing.JLabel
import java.awt.Dimension
import org.dizem.intellipainter.ui.MainApp

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-22
 * Time: 10:41:48
 */

class StatePanel extends JPanel {
    JLabel labelState
    JLabel labelTitle

    StatePanel() {
        setLayout(new BorderLayout())
        labelState = new JLabel('state')
        labelTitle = new JLabel(" ${MainApp.TITLE} ")

        labelTitle.setPreferredSize(new Dimension(450, 23))

        add("West", labelTitle)
        add("Center", labelState)
    }
}
