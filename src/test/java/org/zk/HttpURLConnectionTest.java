package org.zk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zhangkang
 * @date 2025/1/4 20:30
 */
@Slf4j
public class HttpURLConnectionTest {

    @Test
    @SneakyThrows
    public void test() {
        // 默认header中设置了Connection:keep-alive，所以会使用长连接
        // 服务端根据Connection是否关闭连接
        for (int i = 0; i < 100; i++) {
            URL url = new URL("http://localhost:9999");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 关闭长连接
            connection.setRequestProperty("Connection", "close");
            connection.setRequestMethod("GET");
            // 发送请求
            connection.connect();

            // 读取响应
            log.info("response: {}", connection.getResponseCode());
            InputStream inputStream = connection.getInputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(bytes)) != -1) {
                log.info("响应内容 {}", new String(bytes, 0, len));
            }

            inputStream.close();
            connection.disconnect();
            Thread.sleep(1000);
        }
    }
}
