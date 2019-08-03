package org.zk.observe;


import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Observable;
import java.util.Observer;

public class IntervalSwingWindow extends JFrame implements Observer {

    private JLabel startLabel = new JLabel("startField");
    private JLabel endLabel = new JLabel("endField");
    private JLabel lengthLabel = new JLabel("lengthField");
    private JTextField startField = new JTextField();
    private JTextField endField = new JTextField();
    private JTextField lengthField = new JTextField();

    Interval subject = new Interval();

    public IntervalSwingWindow() {
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        GridLayout grid =new GridLayout(3,2);
        c.setLayout(grid);
        c.add(startLabel);
        c.add(startField);
        c.add(endLabel);
        c.add(endField);
        c.add(lengthLabel);
        c.add(lengthField);
        setVisible(true);
        // 添加观察者
        subject.addObserver(this);
        update(subject, null);

        startField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                subject.setStart(Integer.valueOf(startField.getText()));
            }
        });

        endField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                subject.setEnd(Integer.valueOf(endField.getText()));
            }
        });

        lengthField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                subject.setLength(Integer.valueOf(lengthField.getText()));
            }
        });
    }

    public static void main(String[] args) {
        new IntervalSwingWindow();
    }

    @Override
    public void update(Observable o, Object arg) {
        // 响应Interval对象的通告
        System.out.println("update by subject");
        startField.setText(String.valueOf(subject.getStart()));
        endField.setText(String.valueOf(subject.getEnd()));
        lengthField.setText(String.valueOf(subject.getLength()));
    }
}
