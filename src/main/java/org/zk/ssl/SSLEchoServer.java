package org.zk.ssl;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;


//服务端
@Slf4j
public class SSLEchoServer {
    //服务端SSL监听套接字
    static SSLServerSocket serverSocket;


    private static final String KEYSTORE_FILE = ResourceUtil.getResource("jks/two-way-auth/server.jks").getPath();

    private static final String KEYSTORE_PASSWORD = "123456";

    /**
     * 1. 生成服务端秘钥库 keytool -genkey -alias server -keyalg RSA -keysize 2048 -validity 365 -keystore E:/server.jks
     * 2. 生成客户端秘钥库 keytool -genkey -alias client -keyalg RSA -keysize 2048 -validity 365 -keystore E:/client.jks
     * <p>
     * 3. 导出服务端证书 keytool -export -alias server -keystore E:/server.jks -file E:/server.cer
     * 4. 将服务端证书导入到客户端密钥库 keytool -import -trustcacerts -alias server -file E:/server.cer -keystore E:/client.jks
     * <p>
     * 5.导出客户端证书 keytool -export -alias client -keystore E:/client.jks -file E:/client.cer
     * 6.将客户端证书导入到服务端 keytool -import -trustcacerts -alias client -file E:/client.cer -keystore E:/server.jks
     * <p>
     * 查看秘钥 keytool -list -v -keystore E:/server.jks
     * 查看证书 keytool -printcert -file server.cer
     */
    @SneakyThrows
    public static void start() {
        //创建服务端SSL上下文实例
        SSLContext serverSSLContext = SSLContextHelper.createSslContext(KEYSTORE_PASSWORD, KEYSTORE_FILE);
        SSLServerSocketFactory sslServerSocketFactory = serverSSLContext.getServerSocketFactory();
        //通过服务端SSL上下文实例，创建服务端SSL监听套接字
        serverSocket = (SSLServerSocket)
                sslServerSocketFactory.createServerSocket(8888);
        // 单向认证：不校验客户端证书，只需要将服务端证书导入客户端
//        serverSocket.setNeedClientAuth(false);
        // 双向认证：校验客户端证书
        serverSocket.setNeedClientAuth(true);
        //在握手的时候，使用服务端模式
        serverSocket.setUseClientMode(false);

        String[] supported = serverSocket.getEnabledCipherSuites();
        serverSocket.setEnabledCipherSuites(supported);
        log.info("SSL Server started");
        // 监听和接收客户端连接
        while (!Thread.interrupted()) {
            Socket client = serverSocket.accept();
            log.info("客户端连接成功 {}", client.getRemoteSocketAddress());
            // 向客户端发送接收到的字节序列
            OutputStream output = client.getOutputStream();
            // 当一个普通 socket 连接上来, 这里会抛出异常
            InputStream input = client.getInputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            StringBuffer buffer = new StringBuffer();
            while ((len = input.read(buf)) != -1) {
                String sf = new String(buf, 0, len, "UTF-8");
                log.info("服务端收到：{}", sf);
                buffer.append(sf);
                if (sf.contains("\r\n\r\n")) {
                    break;
                }
            }
            //发送消息到客户端
            log.info("发送消息到客户端 {}", buffer);
            output.write(buffer.toString().getBytes("UTF-8"));
            output.flush();
            // 关闭socket连接
            input.close();
            output.close();
            client.close();
        }
        serverSocket.close();
    }

    public static void main(String[] args) {
        start();
    }

}
