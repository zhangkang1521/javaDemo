package org.zk.nullObject;

public class NullObjectTest {
    public static void main(String[] args) {
        Site site = new Site();
        Customer customer = site.getCustomer();
        BillingPlan plan;
        if (customer == null) {
            plan = BillingPlan.basic();
        } else {
            plan = customer.getPlan();
        }

        String customerName;
        if (customer == null) {
            customerName = "occupant";
        } else {
            customerName = customer.getName();
        }

        int weeksDelingquent;
        if (customer == null) {
            weeksDelingquent = 0;
        } else {
            weeksDelingquent = customer.getHistory().getWeekDelinquentInLastYear();
        }
    }
}
