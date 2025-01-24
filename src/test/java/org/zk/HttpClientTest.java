package org.zk;

import lombok.SneakyThrows;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.zk.utils.HttpClientHelper;

/**
 * @author zhangkang
 * @date 2025/1/4 21:10
 */
public class HttpClientTest {

    @Test
    @SneakyThrows
    public void test() {
        // 是长连接，每次端口都相同
        for (int i = 0; i < 100; i++) {
            HttpClientHelper.get("http://localhost:9999/test");
            Thread.sleep(1000);
        }
    }
}
