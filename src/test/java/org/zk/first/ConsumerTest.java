package org.zk.first;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zhangkang on 2019/7/20.
 */
public class ConsumerTest {

    //    mvn clean test org.jacoco:jacoco-maven-plugin:0.8.4:prepare-agent install -Dmaven.test.failure.ignore=true

    Consumer consumer = new Consumer("zk");

    @Before
    public void before() {
        Movie regularMovie = new Movie("regular movie", Movie.REGULAR);
        Movie newReleaseMovie = new Movie("newReleaseMovie", Movie.NEW_RELEASE);
        Movie childMovie = new Movie("childMovie", Movie.CHILDRENS);
        consumer.addRental(new Rental(regularMovie, 2));
        consumer.addRental(new Rental(regularMovie, 5));
        consumer.addRental(new Rental(newReleaseMovie, 1));
        consumer.addRental(new Rental(newReleaseMovie, 2));
        consumer.addRental(new Rental(childMovie, 2));
        consumer.addRental(new Rental(childMovie, 5));
    }

    @Test
    public void statement() throws Exception {
        Assert.assertEquals("Rental Record for zk\n" +
                "\tregular movie\t2.0\n" +
                "\tregular movie\t6.5\n" +
                "\tnewReleaseMovie\t3.0\n" +
                "\tnewReleaseMovie\t6.0\n" +
                "\tchildMovie\t1.5\n" +
                "\tchildMovie\t4.5\n" +
                "Amount owed is 23.5\n" +
                "You earned 7 frequent renter points", consumer.statement());
    }

    @Test
    public void testHtmlStatement() {
        Assert.assertEquals("<h1>Rental Record for zk</h1>\n" +
                "\tregular movie\t2.0<br>\n" +
                "\tregular movie\t6.5<br>\n" +
                "\tnewReleaseMovie\t3.0<br>\n" +
                "\tnewReleaseMovie\t6.0<br>\n" +
                "\tchildMovie\t1.5<br>\n" +
                "\tchildMovie\t4.5<br>\n" +
                "<p>Amount owed is 23.5\n" +
                "You earned 7 frequent renter points</p>", consumer.htmlStatement());
    }

}