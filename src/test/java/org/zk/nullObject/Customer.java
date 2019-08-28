package org.zk.nullObject;

/**
 * 顾客
 */
public class Customer {

    private String name;
    private BillingPlan plan;
    private PaymentHistory paymentHistory;

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
