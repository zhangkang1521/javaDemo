package org.zk.ssl;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 定制的信任管理器
 */
@Slf4j
public final class X509TrustManagerFacade implements X509TrustManager {
    /**
     * 内部的x509TrustManager委托成员
     */
    private X509TrustManager x509TrustManager;

    /**
     * 使用密钥仓库初始化信任管理器
     *
     * @param keyStore 密钥仓库
     */
    public void init(KeyStore keyStore) throws Exception {
        log.info("使用KeyStore初始化信任管理器");
        TrustManagerFactory factory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        //使用密钥仓库初始化信任管理器工厂
        factory.init(keyStore);
        TrustManager[] trustManagers = factory.getTrustManagers();
        // 从信任管理器工厂的信任库中，筛选出X509格式的证书库
        for (int i = 0; i < trustManagers.length; i++) {
            TrustManager trustManager = trustManagers[i];
            if (trustManager instanceof X509TrustManager) {
                this.x509TrustManager = (X509TrustManager) trustManager;
            }
        }
        if (this.x509TrustManager == null) {
            throw new Exception("Couldn't find X509TrustManager");
        }
    }


    public final void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        log.info("校验客户端证书 {}", authType);
        x509TrustManager.checkClientTrusted(chain, authType);
    }

    public final void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        log.info("校验服务端证书 {}", authType);
        x509TrustManager.checkServerTrusted(chain, authType);
    }

    //返回受信任的X509证书数组
    public final X509Certificate[] getAcceptedIssuers() {
        return x509TrustManager.getAcceptedIssuers();
    }


}