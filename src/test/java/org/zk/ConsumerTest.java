package org.zk;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhangkang on 2019/7/20.
 */
public class ConsumerTest {

    @Test
    public void statement() throws Exception {
        Consumer consumer = new Consumer("zk");
        Movie regularMovie = new Movie("regular movie", Movie.REGULAR);
        Movie newReleaseMovie = new Movie("newReleaseMovie", Movie.NEW_RELEASE);
        Movie childMovie = new Movie("childMovie", Movie.CHILDRENS);
        consumer.addRental(new Rental(regularMovie, 2));
        consumer.addRental(new Rental(regularMovie, 5));
        consumer.addRental(new Rental(newReleaseMovie, 2));
        consumer.addRental(new Rental(childMovie, 2));
        consumer.addRental(new Rental(childMovie, 5));
//        System.out.println(consumer.statement());
        Assert.assertEquals("Rental Record for zk\n" +
                "\tregular movie\t2.0\n" +
                "\tregular movie\t6.5\n" +
                "\tnewReleaseMovie\t6.0\n" +
                "\tchildMovie\t1.5\n" +
                "\tchildMovie\t4.5\n" +
                "Amount owed is 20.5\n" +
                "You earned 6 frequent renter points", consumer.statement());
    }

}