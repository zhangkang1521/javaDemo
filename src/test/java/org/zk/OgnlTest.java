package org.zk;

import ognl.DefaultClassResolver;
import ognl.MemberAccess;
import ognl.Ognl;
import org.junit.Test;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

public class OgnlTest {


    @Test
    public void test1() throws Exception{
        Map<String, Object> root = new HashMap<>();
        root.put("username", "");
        Map context = Ognl.createDefaultContext(root, new DefaultMemberAccess(), null, null);
        System.out.println(Ognl.getValue("username != null and username!=''", context, root));
        System.out.println(Ognl.getValue("@org.zk.StringUtils@isNotBlank(username)", context, root));
    }
}
