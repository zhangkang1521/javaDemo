package org.zk;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.dozer.DozerBeanMapper;

public class DozerTest {

    private User user;

    @Before
    public void before() {
        user = new User();
        user.setId(1);
        user.setVAccount("123");
    }

    @Test
    public void test1() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        User user2 = mapper.map(user, User.class);
        Assert.assertEquals(user.getVAccount(), user2.getVAccount());
    }

    @Test
    public void test2() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        User user2 = mapper.map(user, User.class);
        Assert.assertEquals(user.getId(), user2.getId());
        Assert.assertEquals(user.getVAccount(), user2.getVAccount());
    }
}
