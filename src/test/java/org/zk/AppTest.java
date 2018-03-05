package org.zk;


import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest  {

    @Test
    public void testApp() {
        User user = new User();
        user.setId(100);
        user.setUsername("zk");
        System.out.println(user);
    }
}
