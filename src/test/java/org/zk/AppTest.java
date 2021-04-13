package org.zk;


import com.opencsv.CSVWriter;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test1() throws Exception {
        OutputStream outputStream = new FileOutputStream(new File("G:/test.csv"));
        try (CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream, "GBK"))) {
            csvWriter.writeNext(new String[]{"a", "b"});
            String [] row = new String[2];
            row[0] = "黄丽萍"; // 这个人的名字使用UTF-8，2列变成1列，但是读的时候没问题，先不解决
            row[1] = "xx";
            csvWriter.writeNext(row);
        }
    }
}
