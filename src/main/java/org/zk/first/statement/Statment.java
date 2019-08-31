package org.zk.first.statement;

import org.zk.first.Consumer;
import org.zk.first.Rental;

/**
 * 模板方法
 */
public abstract class Statment {

    public String value(Consumer consumer) {
        String result = headString(consumer);
        for (Rental rental : consumer.getRentals()) {
            result += eachRentalString(rental);
        }
        result += footerString(consumer);
        return result;
    }

    public abstract String headString(Consumer consumer);

    public abstract String eachRentalString(Rental rental);
    
    public abstract String footerString(Consumer consumer);

}
