package org.zk;

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
        double totalAmount = 0;
        int frequentRenterPoint = 0;
        String result = "Rental Record for " + getName() + "\n";
        for (Rental rental : rentals) {
            double thisAmount = 0;
            // 计算金额
            switch (rental.getMovie().getPriceCode()) {
                case Movie.REGULAR:
                    thisAmount += 2;
                    if (rental.getDaysRented() > 2) {
                        thisAmount += (rental.getDaysRented() - 2) * 1.5;
                    }
                    break;
                case Movie.NEW_RELEASE:
                    thisAmount = rental.getDaysRented() * 3;
                    break;
                case Movie.CHILDRENS:
                    thisAmount += 1.5;
                    if (rental.getDaysRented() > 3) {
                        thisAmount += (rental.getDaysRented() - 3) * 1.5;
                    }
                    break;
            }
            // 积分
            frequentRenterPoint++;
            if (rental.getMovie().getPriceCode() == Movie.NEW_RELEASE && rental.getDaysRented() > 1) {
                frequentRenterPoint++;
            }
            result += "\t" + rental.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + "\n";
            // 累加总金额
            totalAmount += thisAmount;
        }
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoint) + " frequent renter points";
        return result;
    }
}
