package org.zk;


import lombok.NonNull;

/**
 * Created by Administrator on 3/5/2018.
 */
public class NotNullDemo {

    public static void test(@NonNull Integer person) {
        System.out.println(person.toString());
    }

    public static void main(String[] args) {
        test(null);
    }
}
