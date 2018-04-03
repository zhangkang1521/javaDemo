package org.zk;

import org.junit.Test;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by zhangkang on 2018/3/27.
 */
public class DynamicColumn {

    @Test
    public void test1() throws Exception {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Employee employee = new Employee();
            employee.setName("zk" + i);
            employee.setBirthday("199088888888" + i);
            employee.setPayment("2000" + i * 1000);
            employees.add(employee);
        }
        try(InputStream is = new FileInputStream("G:/test.xls")) {
            try(OutputStream os = new FileOutputStream("target/grid_output2.xls")) {
                Context context = new Context();
                context.putVar("headers", Arrays.asList("姓名", "生日", "薪水"));
                context.putVar("data", employees);
                context.putVar("time", "2018-03-27");
                JxlsHelper.getInstance().processGridTemplateAtCell(is, os, context, "name,birthday,payment", "Sheet1!A1");
            }
        }
    }

    @Test
    public void test2() {
        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("2");
        String[] tmp = new String[set.size()];
        String[] arr = set.toArray(tmp);
    }

    @Test
    public void test3() {
        char[] prefix = new char[]{'a', 'b'};
        char[] body = new char[]{'c', 'd', 'e'};
        char[] newBody = new char[5];
        System.arraycopy(prefix, 0, newBody, 0, prefix.length);
        System.arraycopy(body, 0, newBody, prefix.length, body.length);
        System.out.println(newBody);
    }
}
