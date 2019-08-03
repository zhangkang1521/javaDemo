package org.zk;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class IntervalWindow extends Frame {
    private Label startLabel = new Label("startField");
    private Label endLabel = new Label("endField");
    private Label lengthLabel = new Label("lengthField");
    private TextField startField = new TextField();
    private TextField endField = new TextField();
    private TextField lengthField = new TextField();

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
        calculateLength();
    }

    private void endFieldFocusLost(FocusEvent e) {
        calculateLength();
    }

    private void lengthFieldFocusLost(FocusEvent e) {
        calculateEnd();
    }

    private void calculateEnd() {
        int start = Integer.parseInt(startField.getText());
        int length = Integer.parseInt(lengthField.getText());
        int end = start + length;
        endField.setText(String.valueOf(end));
    }

    private void calculateLength() {
        int start = Integer.parseInt(startField.getText());
        int end = Integer.parseInt(endField.getText());
        int length = end - start;
        lengthField.setText(String.valueOf(length));
    }

    public static void main(String[] args) {
       new IntervalWindow();
    }
}
