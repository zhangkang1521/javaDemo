package org.zk;


import com.thoughtworks.xstream.XStream;
import org.junit.Test;

import java.util.Arrays;

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
        User.Record record1 = new User.Record();
        record1.setCustomerSalarySeq("111");

        User.Record record2 = new User.Record();
        record2.setCustomerSalarySeq("222");

        user.setRecords(Arrays.asList(record1, record2));
        System.out.println( xStream.toXML(user));
    }

    @Test
    public void testFormXML() {
        String xml = "<org.zk.User>\n" +
                "  <id>100</id>\n" +
                "  <username>zk</username>\n" +
                "  <body class=\"org.zk.B\">\n" +
                "    <b>aa</b>\n" +
                "  </body>\n" +
                "</org.zk.User>";
        XStream xStream = new XStream();
        User user = new User();
        user.setAge(10);
        User user2 = (User)xStream.fromXML(xml);
        System.out.println(user);
    }
}
