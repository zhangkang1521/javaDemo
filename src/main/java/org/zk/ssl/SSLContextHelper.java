package org.zk.ssl;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;


@Slf4j
public class SSLContextHelper {

    public static SSLContext createSslContext(String pass, String keyStoreFile) throws Exception {
        // keyStore
        char[] passArray = pass.toCharArray();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        FileInputStream inputStream = new FileInputStream(keyStoreFile);
        keyStore.load(inputStream, passArray);

        //调用自定义的方法，创建上下文
        SSLContext sslContext = createSslContext(passArray, keyStore);

        inputStream.close();
        return sslContext;
    }

    public static SSLContext createSslContext(char[] passArray, KeyStore keyStore) throws Exception {
        // 初始化keyManagerFactory
        String algorithm = KeyManagerFactory.getDefaultAlgorithm();
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(algorithm);
        keyManagerFactory.init(keyStore, passArray);

        // 创建sslContext
        SSLContext sslContext = SSLContext.getInstance("SSL");

        //信任库
        //如果是单向认证，服务端不需要验证客户端的合法性，此时，TrustManager 可以为空
        X509TrustManagerFacade facade = new X509TrustManagerFacade();
        facade.init(keyStore);
        TrustManager[] trustManagers = new TrustManager[]{facade};

        // 初始化sslContext, 安全随机数不需要设置
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagers, null);

        return sslContext;
    }

}
