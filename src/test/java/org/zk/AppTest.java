package org.zk;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test1() {
        // https://nosec.org/home/detail/2933.html
        JSON.parse("{\"a\":\"\\x");
    }

    @Test
    public void test2() {
       Map obj =  JSON.parseObject("{\"type\":\"timeConvert\",\"value\":[{\"column\":2,\"pattern\":\"yyyy-MM-dd\"},{\"column\":3,\"pattern\":\"HH:mm:ss\"}]}", Map.class);
       String value = JSON.toJSONString(obj.get("value"));
       Object config = JSON.parseArray(value, TimeConvertConfig.class);
    }

    static class TimeConvertConfig {
        private int column;
        private String pattern;

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }
    }
}
