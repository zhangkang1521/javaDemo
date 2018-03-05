package org.zk;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 3/5/2018.
 */
public class DateUtilsTest {

    @Test
    public void test1() throws Exception{
        System.out.println(DateUtils.addDays(new Date(), 31));
        System.out.println(DateUtils.parseDate("2018/03/05", "yyyy-MM-dd", "yyyy/MM/dd"));

    }

    @Test
    public void testCeilingTruncate() {
        System.out.println(DateUtils.ceiling(new Date(), Calendar.DAY_OF_MONTH));
        System.out.println(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
    }
}
