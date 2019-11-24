package org.zk;

public class MyDriverImpl implements MyDriver {
    static {
        System.out.println("register MyDriver to DriverManager");
    }
}
