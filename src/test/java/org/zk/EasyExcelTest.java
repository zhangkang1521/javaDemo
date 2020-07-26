package org.zk;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.poi.util.LocaleUtil;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class EasyExcelTest {

	@Test
	public void testRead() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
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

	@Test
	public void testDate() throws Exception {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));

		Calendar calendar1 = LocaleUtil.getLocaleCalendar();
		calendar1.set(1900,0, 32982 - 1, 0, 0, 0);
		Date date1 = calendar1.getTime();


		Calendar calendar2 = LocaleUtil.getLocaleCalendar();
		calendar1.set(1900,0, 43940 - 1, 0, 0, 0);
		Date date2 = calendar2.getTime();
	}
}
