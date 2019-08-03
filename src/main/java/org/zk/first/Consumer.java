package org.zk.first;

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

    public String statement() {
        String result = "Rental Record for " + getName() + "\n";
        for (Rental rental : rentals) {
            result += "\t" + rental.getMovie().getTitle() + "\t" + String.valueOf(rental.getCharge()) + "\n";
        }
        result += "Amount owed is " + String.valueOf(getTotalCharge()) + "\n";
        result += "You earned " + String.valueOf(getTotalFrequentRenterPoint()) + " frequent renter points";
        return result;
    }

    public String htmlStatement() {
        String result = "<h1>Rental Record for " + getName() + "</h1>\n";
        for (Rental rental : rentals) {
            result += "\t" + rental.getMovie().getTitle() + "\t" + String.valueOf(rental.getCharge()) + "<br>\n";
        }
        result += "<p>Amount owed is " + String.valueOf(getTotalCharge()) + "\n";
        result += "You earned " + String.valueOf(getTotalFrequentRenterPoint()) + " frequent renter points</p>";
        return result;
    }

    private double getTotalCharge() {
        double totalAmount = 0;
        for (Rental rental : rentals) {
            totalAmount += rental.getCharge();
        }
        return totalAmount;
    }

    private long getTotalFrequentRenterPoint() {
        int frequentRenterPoint = 0;
        for (Rental rental : rentals) {
            frequentRenterPoint += rental.getFrequentRenterPoints();
        }
        return frequentRenterPoint;
    }





}
