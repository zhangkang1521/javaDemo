package org.zk;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class AppTest  {

    @Test
    public void test1() throws Exception {
        User user = new User();
        user.setUsername("zk");
        String result = new ObjectMapper().writeValueAsString(user);
        System.out.println(result);
    }

}
