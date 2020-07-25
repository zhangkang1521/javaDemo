package org.zk;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.junit.Test;

public class EasyExcelTest {

	@Test
	public void testRead() {
		EasyExcel.read("E:/test.xlsx", User.class, new AnalysisEventListener<User>() {

			@Override
			public void invoke(User user, AnalysisContext analysisContext) {
				System.out.println(user);
			}

			@Override
			public void doAfterAllAnalysed(AnalysisContext analysisContext) {

			}
		}).sheet().doRead();
	}
}
