package org.zk.first.statement;

import org.zk.first.Consumer;
import org.zk.first.Rental;

public class HtmlStatement extends Statment {

    public String headString(Consumer consumer) {
        return "<h1>Rental Record for " + consumer.getName() + "</h1>\n";
    }

    public String eachRentalString(Rental rental) {
        return "\t" + rental.getMovie().getTitle() + "\t" + String.valueOf(rental.getCharge()) + "<br>\n";
    }

    public String footerString(Consumer consumer) {
        return "<p>Amount owed is " + String.valueOf(consumer.getTotalCharge()) + "\n"
         + "You earned " + String.valueOf(consumer.getTotalFrequentRenterPoint()) + " frequent renter points</p>";
    }



}
