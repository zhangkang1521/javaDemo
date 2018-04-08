package org.zk;


import com.fasterxml.jackson.databind.DeserializationFeature;
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

    @Test
    public void test2() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        User user = mapper.readValue("{\"id\":20,\"username2\":\"zk\"}", User.class);
        System.out.println(user);
    }

}
