package ru.kushaevaa.gui;

import javax.swing.*;

public class TextAreaImp extends JTextArea {
    @Override
    public void append(String str) {
        super.append(str+"\n");
    }
}
