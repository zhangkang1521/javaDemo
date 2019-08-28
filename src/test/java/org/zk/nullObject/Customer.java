package org.zk.nullObject;

/**
 * 顾客
 */
public class Customer {

    private String name;
    private BillingPlan plan;
    private PaymentHistory paymentHistory;

    public boolean isNull() {
        return false;
    }

    public static Customer newNull() {
        return new NullCustomer();
    }

    public String getName() {
        return name;
    }

    public BillingPlan getPlan() {
        return plan;
    }

    public PaymentHistory getHistory() {
        return paymentHistory;
    }
}
