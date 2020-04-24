package org.zk;


import freemarker.cache.FileTemplateLoader;
import freemarker.ext.servlet.AllHttpScopesHashModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class FreeMarkerTest {


    @Test
    public void test1() throws Exception {
        Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File("E:/"));

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("username", "zk");

        Template template = configuration.getTemplate("hello.ftl");
        File docFile = new File("E:/hello.txt");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
        template.process(dataMap, out);
        out.flush();
    }

}
