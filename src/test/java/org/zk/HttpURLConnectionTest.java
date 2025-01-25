package org.zk;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.zk.ssl.SSLContextHelper;

import javax.net.ssl.*;
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
            URL url = new URL("http://localhost:8888");
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

    @Test
    @SneakyThrows
    public void testHttps() {
        //创建客户端SSLContext上下文
        final String KEYSTORE_FILE = ResourceUtil.getResource("jks/one-way-auth/client.jks").getPath();
        SSLContext clientSSLContext = SSLContextHelper.createSslContext("123456", KEYSTORE_FILE);
        //创建安全套接字工厂
        HttpsURLConnection.setDefaultSSLSocketFactory(clientSSLContext.getSocketFactory());

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession sslSession) {
                return "localhost".equals(hostname);
            }
        });

        URL url = new URL("https://localhost:8888");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        // 关闭长连接
//        connection.setRequestProperty("Connection", "close");
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
    }
}
