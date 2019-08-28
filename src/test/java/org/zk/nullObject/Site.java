package org.zk.nullObject;

/**
 * 地点（场所）
 */
public class Site {
    private Customer customer;

    public Customer getCustomer() {
        return customer == null ? Customer.newNull() : customer;
    }
}
