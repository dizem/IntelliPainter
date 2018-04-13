package org.dizem.intellipainter.logger;

import org.dizem.intellipainter.ui.MainApp;

import javax.swing.JTextArea;

import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-9-24
 * Time: 12:30:09
 */
public class Log {
    JTextArea textArea = new JTextArea();

    Log(MainApp app) {
        app.logTextArea = textArea;
    }

    public void info(String msg) {
        show(msg, "INFO");
    }

    void show(String msg, String tag) {
        SimpleDateFormat tempDate = new SimpleDateFormat("hh:mm:ss");
        String datetime = tempDate.format(new java.util.Date());
        textArea.append("[" + datetime + "]");
        textArea.append(msg + '\n');
        int length = textArea.getText().length();
        textArea.setCaretPosition(length);
    }
}
