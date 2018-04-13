package org.dizem.intellipainter.ui.panel

import javax.swing.JPanel
import javax.swing.JTextArea
import java.awt.BorderLayout
import java.awt.Font

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-29
 * Time: 0:29:30
 */
class AboutPanel extends JPanel {

    def textArea = new JTextArea()

    AboutPanel() {
        setLayout(new BorderLayout())
        textArea.setFont new Font(null, 0, 20)
        textArea.setEditable false
        textArea.append """
		Name: IntelliPainter

		Version: 4.0

		Language: Groovy 1.7.4

		Last update: 2010/10/27 17.16
		
		Author: Ding Zhimin

		Email: dizem@126.com
		"""
        add(textArea, BorderLayout.CENTER);
    }
}
