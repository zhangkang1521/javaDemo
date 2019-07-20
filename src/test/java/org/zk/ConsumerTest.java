package org.zk;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhangkang on 2019/7/20.
 */
public class ConsumerTest {

    //    mvn clean test org.jacoco:jacoco-maven-plugin:0.8.4:prepare-agent install -Dmaven.test.failure.ignore=true


    @Test
    public void statement() throws Exception {
        Consumer consumer = new Consumer("zk");
        Movie regularMovie = new Movie("regular movie", Movie.REGULAR);
        Movie newReleaseMovie = new Movie("newReleaseMovie", Movie.NEW_RELEASE);
        Movie childMovie = new Movie("childMovie", Movie.CHILDRENS);
        consumer.addRental(new Rental(regularMovie, 2));
        consumer.addRental(new Rental(regularMovie, 5));
//        consumer.addRental(new Rental(newReleaseMovie, 2));
        consumer.addRental(new Rental(childMovie, 2));
        consumer.addRental(new Rental(childMovie, 5));
        Assert.assertEquals("Rental Record for zk\n" +
                "\tregular movie\t2.0\n" +
                "\tregular movie\t6.5\n" +
//                "\tnewReleaseMovie\t6.0\n" +
                "\tchildMovie\t1.5\n" +
                "\tchildMovie\t4.5\n" +
                "Amount owed is 14.5\n" +
                "You earned 4 frequent renter points", consumer.statement());
    }

}