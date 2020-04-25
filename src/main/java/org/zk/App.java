package org.zk;

import java.io.InputStream;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        InputStream in = App.class.getClassLoader().getResourceAsStream("a.txt");
        System.out.println(in);
    }
}
