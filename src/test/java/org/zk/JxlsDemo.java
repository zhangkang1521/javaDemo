package org.zk;

import org.junit.Before;
import org.junit.Test;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class JxlsDemo {

	List<Employee> employees = new ArrayList<>();

	@Before
	public void before() {
		for (int i = 0; i < 4; i++) {
			Employee employee = new Employee();
			employee.setName("zk" + i);
			employee.setBirthday("20200401");
			employee.setPayment("2000" + i * 1000);
			employees.add(employee);
		}
	}

	@Test
	public void testVersion2() throws Exception {
		// jx:area(lastCell="B1")
		// jx:each(items="users" var="item" lastCell="B3")
		try (InputStream is = JxlsDemo.class.getClassLoader().getResourceAsStream("template_version2.xls");
		     OutputStream os = new FileOutputStream("target/template_version2_out.xls")) {
			Context context = new Context();
			context.putVar("year", 2015);
			context.putVar("users", employees);
			JxlsHelper.getInstance().processTemplate(is, os, context);
		}
	}

	@Test
	public void testVersion1() throws Exception {
//		Map<String, Object> map = new HashMap<>();
//		map.put("year", "2020");
//		map.put("users", employees);
//		XLSTransformer transformer = new XLSTransformer();
//		transformer.transformXLS("E:/demo.xls", map, "E:/demo-out.xls");
	}
}
