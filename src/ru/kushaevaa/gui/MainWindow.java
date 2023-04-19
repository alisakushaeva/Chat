package ru.kushaevaa.gui;

import ru.kushaevaa.net.ChatEvent;
import ru.kushaevaa.net.ChatListener;
import ru.kushaevaa.net.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {
    private JLabel lbl;
    private JTextField tf;
    private JButton btn;
    private JTextArea ta;
    private JScrollPane sp;
    private Client client;
    public MainWindow(){

        setSize(600,450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        lbl = new JLabel("");
        tf = new JTextField();
        btn = new JButton("Отправить");
        ta = new TextAreaImp();
        sp = new JScrollPane(ta);

        //region HorVer
        GroupLayout gl = new GroupLayout(getContentPane());
        setLayout(gl);
        gl.setHorizontalGroup(
                gl.createSequentialGroup()
                        .addGap(8)
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(lbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                                        .addGroup(
                                                gl.createSequentialGroup()
                                                        .addComponent(tf, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                                                        .addGap(4)
                                                        .addComponent(btn, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                        )
                                        .addComponent(sp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE))
                        .addGap(8)
        );
        gl.setVerticalGroup(
                gl.createSequentialGroup()
                        .addGap(8)
                        .addComponent(lbl, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(8)
                        .addGroup(
                                gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(tf, 27, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        )
                        .addGap(8)
                        .addComponent(sp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                        .addGap(8)
        );
        //endregion
        try {
            client = new Client("localhost", 5678);
            client.start();
        } catch (IOException e) {
            getMessage("Ошибка: " + e.getMessage());
        }
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(tf.getText());
                tf.setText("");
            }
        }
        );
        client.addListener(new ChatListener() {
            @Override
            public void chatAvailiable(ChatEvent e) {
                ta.append(e.getMessage1());
            }
        });
    }
    public void getMessage(String msg) {
            ta.append(msg);
    }
    public void sendMessage(String msg)
    {
        client.send(msg);
    }
}

