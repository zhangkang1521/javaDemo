package org.zk.observe;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Observable;
import java.util.Observer;

public class IntervalWindow extends Frame implements Observer {
    private Label startLabel = new Label("startField");
    private Label endLabel = new Label("endField");
    private Label lengthLabel = new Label("lengthField");
    private TextField startField = new TextField();
    private TextField endField = new TextField();
    private TextField lengthField = new TextField();

    private Interval subject;

    public IntervalWindow() {
        startLabel.setBounds(30, 50, 80, 30);
        endLabel.setBounds(30, 90, 80, 30);
        lengthLabel.setBounds(30, 140, 80, 30);
        startField.setBounds(130, 50, 150, 30);
        endField.setBounds(130, 90, 150, 30);
        lengthField.setBounds(130, 140, 150, 30);
        startField.setText("0");
        endField.setText("0");
        lengthField.setText("0");
        add(startLabel);
        add(endLabel);
        add(lengthLabel);
        add(startField);
        add(endField);
        add(lengthField);
        setLayout(null);
        setSize(300,300);
        setVisible(true);
        SymFocus symFocus = new SymFocus();
        startField.addFocusListener(symFocus);
        endField.addFocusListener(symFocus);
        lengthField.addFocusListener(symFocus);
        // 观察者
        subject = new Interval();
        subject.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        // 响应Interval对象的通告
        System.out.println("update by subject");
        startField.setText(String.valueOf(subject.getStart()));
        endField.setText(String.valueOf(subject.getEnd()));
        lengthField.setText(String.valueOf(subject.getLength()));

    }

    class SymFocus extends FocusAdapter {
        @Override
        public void focusLost(FocusEvent e) {
            Object obj = e.getSource();
            if (obj == startField) {
                startFieldFocusLost(e);
            } else if (obj == endField) {
                endFieldFocusLost(e);
            } else if (obj == lengthField) {
                lengthFieldFocusLost(e);
            }
        }
    }

    private void startFieldFocusLost(FocusEvent e) {
        System.out.println("start field lost");
        setStart(startField.getText());
    }

    private void endFieldFocusLost(FocusEvent e) {
        System.out.println("end field lost");
        setEnd(endField.getText());
    }

    private void lengthFieldFocusLost(FocusEvent e) {
        System.out.println("length field lost");
        setLength(lengthField.getText());
    }

    public void setStart(String start) {
        subject.setStart(Integer.valueOf(start));
    }

    public void setEnd(String end) {
        subject.setEnd(Integer.valueOf(end));
    }

    public void setLength(String length) {
        subject.setLength(Integer.valueOf(length));
    }

    public static void main(String[] args) {
       new IntervalWindow();
    }
}
