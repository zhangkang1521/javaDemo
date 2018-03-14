package org.zk;


import com.thoughtworks.xstream.XStream;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test1() {
        XStream xStream = new XStream();
        User user = new User();
        user.setId(100);
        user.setUsername("zk");
        System.out.println( xStream.toXML(user));
    }
}
