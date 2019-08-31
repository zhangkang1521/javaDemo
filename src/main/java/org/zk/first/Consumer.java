package org.zk.first;

import org.zk.first.statement.HtmlStatement;
import org.zk.first.statement.TextStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangkang on 2019/7/20.
 */
public class Consumer {
    private String name;
    private List<Rental> rentals = new ArrayList<>();

    public Consumer(String name) {
        this.name = name;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public String getName() {
        return name;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public String statement() {
       return new TextStatement().value(this);
    }

    public String htmlStatement() {
        return new HtmlStatement().value(this);
    }

    public double getTotalCharge() {
        double totalAmount = 0;
        for (Rental rental : rentals) {
            totalAmount += rental.getCharge();
        }
        return totalAmount;
    }

    public long getTotalFrequentRenterPoint() {
        int frequentRenterPoint = 0;
        for (Rental rental : rentals) {
            frequentRenterPoint += rental.getFrequentRenterPoints();
        }
        return frequentRenterPoint;
    }





}
