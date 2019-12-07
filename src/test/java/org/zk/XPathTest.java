package org.zk;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import sun.security.util.Resources;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

public class XPathTest {

    @Test
    public void test1() throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream in = getClass().getClassLoader().getResourceAsStream("user.xml");
        Document doc = builder.parse(in);
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList) xPath.evaluate("/users/*", doc, XPathConstants.NODESET);
        for (int i = 1; i <= nodeList.getLength(); i++) {
            String path = "/users/user[" + i + "]";
            System.out.println(xPath.evaluate(path + "/@id", doc, XPathConstants.STRING));
            System.out.println(xPath.evaluate(path + "/username", doc, XPathConstants.STRING));
            System.out.println(xPath.evaluate(path + "/age", doc, XPathConstants.STRING));
        }
    }
}
