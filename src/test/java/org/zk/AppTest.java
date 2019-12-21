package org.zk;


import org.junit.Test;
import org.junit.runner.Computer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Unit test for simple App.
 */
public class AppTest {

    public void fun(A a) {
        a.read(1);
        a.read(2);
    }

    @Test
    public void test1() {
        new AppTest().fun(new A() {
            public void read(int param) {
                System.out.println(param);
            }
        });
    }

    @Test
    public void test2() {
        new AppTest().fun(param -> {
            System.out.println(param);
        });
    }

    private  static void tryImplementation(Runnable runnable) {
        runnable.run();
    }

    public   static void useSlf4jLogging() {
        System.out.println("use slf4j");
    }

    public static void main(String[] args) {
        AppTest.tryImplementation(AppTest::useSlf4jLogging);
    }

    @Test
    public void test3() {
//        Comparator<User> c = (User c1, User c2) -> c1.getAge().compareTo(c2.getAge());
//        Comparator<User> c = (c1, c2) -> {return c1.getAge().compareTo(c2.getAge());};
        Comparator<User> c = Comparator.comparing(User::getAge);

        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User u = new User();
            u.setAge(new Random().nextInt(100));
            userList.add(u);
        }
        userList.sort(c);
        System.out.println(userList);

        userList.forEach(AppTest::print);
    }

    public static void print(User user) {
        System.out.println("ss:" + user);
    }
}
