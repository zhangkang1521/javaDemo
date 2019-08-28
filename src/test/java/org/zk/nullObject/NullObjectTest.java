package org.zk.nullObject;

public class NullObjectTest {
    public static void main(String[] args) {
        Site site = new Site();
        Customer customer = site.getCustomer();
        BillingPlan plan = customer.getPlan();

        String customerName = customer.getName();

        int weeksDelingquent = customer.getHistory().getWeekDelinquentInLastYear();

    }
}
