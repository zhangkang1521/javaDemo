package org.zk;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class JacksonTest {

    @Test
    public void objectToJson() throws Exception {
        User user = new User();
        user.setUsername("zk");
        String result = new ObjectMapper().writeValueAsString(user);
        System.out.println(result);
    }

    @Test
    public void jsonToObject() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        User user = mapper.readValue("{\"username\":\"zk\"}", User.class);
        Assert.assertEquals("zk", user.getUsername());
    }

    @Test
    public void objectToXml() throws Exception {
        User user = new User();
        user.setUsername("zk");
        user.setMovies(Arrays.asList("a", "b"));
        String result = new XmlMapper().writeValueAsString(user);
        System.out.println(result);
    }

    @Test
    public void xmlToObject() throws Exception{
        XmlMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        User user = mapper.readValue("<user><username>zk</username></user>", User.class);
        Assert.assertEquals("zk", user.getUsername());
    }

}
