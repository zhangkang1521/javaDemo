package org.zk.nullObject;

public class NullCustomer extends Customer {
    public boolean isNull() {
        return true;
    }

    public String getName() {
        return "occupant";
    }

    public PaymentHistory getHistory() {
        return new NullPaymentHistory();
    }

    public BillingPlan getPlan() {
        return BillingPlan.basic();
    }
}
