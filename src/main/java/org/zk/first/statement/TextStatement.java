package org.zk.first.statement;

import org.zk.first.Consumer;
import org.zk.first.Rental;

public class TextStatement extends Statment {

    public String headString(Consumer consumer) {
        return "Rental Record for " + consumer.getName() + "\n";
    }

    public String eachRentalString(Rental rental) {
        return "\t" + rental.getMovie().getTitle() + "\t" + String.valueOf(rental.getCharge()) + "\n";
    }

    public String footerString(Consumer consumer) {
        return "Amount owed is " + String.valueOf(consumer.getTotalCharge()) + "\n" +
        "You earned " + String.valueOf(consumer.getTotalFrequentRenterPoint()) + " frequent renter points";
    }

}
