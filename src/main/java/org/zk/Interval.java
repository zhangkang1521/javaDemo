package org.zk;

import java.util.Observable;

public class Interval extends Observable {

    private int start = 0;
    private int end = 0;
    private int length = 0;

    public void setStart(int start) {
        System.out.println("set start to " + start + ", calculate length and notify");
        this.start = start;
        calculateLength();
        setChanged();
        notifyObservers();
    }

    void setEnd(int end) {
        System.out.println("set end to " + end + ", calculate length and notify");
        this.end = end;
        calculateLength();
        setChanged();
        notifyObservers();
    }

    public void setLength(int length) {
        System.out.println("set length to " + length + ", calculate end and notify");
        this.length = length;
        calculateEnd();
        setChanged();
        notifyObservers();
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getLength() {
        return length;
    }

    private void calculateEnd() {
        end = start +length;
    }

    private void calculateLength() {
        length = end - start;
    }


}
