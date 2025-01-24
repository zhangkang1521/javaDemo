package org.zk;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import lombok.SneakyThrows;
import org.junit.Test;


/**
 * @author zhangkang
 * @date 2025/1/18 21:31
 */
public class RSATest {

    @Test
    public void generateKeyPair() {
        RSA rsa = new RSA();

        // 获取公钥和私钥（以 Base64 编码格式）
        String publicKeyBase64 = rsa.getPublicKeyBase64();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();

        System.out.println("Public Key: " + publicKeyBase64);
        System.out.println("Private Key: " + privateKeyBase64);
    }

    @Test
    @SneakyThrows
    public void rsa() {
        // 创建 RSA 实例，默认生成 2048 位的密钥对
        String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAIMnM+2on45vRVgNGZCUqzw+IXruNEXOuhfkdmRRsOsHYwTJGltm0ykwcFoufJUPHRC+vIxDTPYUEwbXsVdCf7ZbPcFTl9OnbNcRyVsvQE/wfE3WWQsAl4jiqZw5XV5q7iWzLULybewo9sYkp0Rvwkxo4hlLTzA5oONTi5n0Ivc7AgMBAAECgYBkAJlcxm1a2sciG2L51hI27hzzMfwTmkxU+XO4jH18x/YdaQW62c16o5+Xc8i0k4CTCwyTWqGtJKNZXU+wfFkJ3+U329+pY2E4KKjiHDadhwbzniaPp1SB8OxC2a7jxNa+SzZl4mTjV6mgZMkSbCkIVzfVyj0ZkxYo16+MjcT/WQJBAOo4Jvno10KrfaMfKlSYTeOsFP/ibOB3YFIYTIWGsyOFPq/vavrFuH8JUeR91X8hg0c8ue1j9qQ+1h1sa/K5oKcCQQCPWXJddN1NUybG5xQagAo3yEshoG1gN+GtC5EsommVVnlxWebR6YLDxznoetTIjxk+QHMv8NSWm9geI6AOfNNNAkEAseRUNhuS4yA9HFffXsEO540bpf3jk9kXX0L1W7Ui+ieJpTbCmhTyPApoGJM6RIS/oKuOy67Q9Y6EMXTXyFMUoQJBAInTkqZQysaiAFKVszqXiTuBFNvMB93PXOioinjD34h6kGFv8wI45kWetArWruf2zmqQRS/++iekx/KShj3nvrECQQC8gote4141KbjC51m/a0KaN++qODHqupx55ZKgMwV985brg2r5s6xtTQmVrWJLXLlLMkyMmKkGOVH/sumHsJDp";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDJzPtqJ+Ob0VYDRmQlKs8PiF67jRFzroX5HZkUbDrB2MEyRpbZtMpMHBaLnyVDx0QvryMQ0z2FBMG17FXQn+2Wz3BU5fTp2zXEclbL0BP8HxN1lkLAJeI4qmcOV1eau4lsy1C8m3sKPbGJKdEb8JMaOIZS08wOaDjU4uZ9CL3OwIDAQAB";
        RSA rsa = new RSA(privateKey, publicKey);

        // 要加密的原始字符串
        String plainText = "123456";

        // 使用公钥加密
        byte[] encryptData = rsa.encrypt(plainText, KeyType.PublicKey);
        String base64EncryptedData = Base64.encode(encryptData);
        System.out.println("Encrypted: " + base64EncryptedData);

        // 使用私钥解密
        byte[] decryptData = rsa.decrypt(Base64.decode(base64EncryptedData), KeyType.PrivateKey);
        String decryptedText = new String(decryptData, "UTF-8");
        System.out.println("Decrypted: " + decryptedText);

        // 如果你已经有了公钥和私钥，可以直接使用它们创建 RSA 实例
        // RSA rsaWithKeys = new RSA(privateKeyBase64, publicKeyBase64);
    }

    @Test
    @SneakyThrows
    public void rsaSign() {
        // 创建 RSA 实例，默认生成 2048 位的密钥对
        String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAIMnM+2on45vRVgNGZCUqzw+IXruNEXOuhfkdmRRsOsHYwTJGltm0ykwcFoufJUPHRC+vIxDTPYUEwbXsVdCf7ZbPcFTl9OnbNcRyVsvQE/wfE3WWQsAl4jiqZw5XV5q7iWzLULybewo9sYkp0Rvwkxo4hlLTzA5oONTi5n0Ivc7AgMBAAECgYBkAJlcxm1a2sciG2L51hI27hzzMfwTmkxU+XO4jH18x/YdaQW62c16o5+Xc8i0k4CTCwyTWqGtJKNZXU+wfFkJ3+U329+pY2E4KKjiHDadhwbzniaPp1SB8OxC2a7jxNa+SzZl4mTjV6mgZMkSbCkIVzfVyj0ZkxYo16+MjcT/WQJBAOo4Jvno10KrfaMfKlSYTeOsFP/ibOB3YFIYTIWGsyOFPq/vavrFuH8JUeR91X8hg0c8ue1j9qQ+1h1sa/K5oKcCQQCPWXJddN1NUybG5xQagAo3yEshoG1gN+GtC5EsommVVnlxWebR6YLDxznoetTIjxk+QHMv8NSWm9geI6AOfNNNAkEAseRUNhuS4yA9HFffXsEO540bpf3jk9kXX0L1W7Ui+ieJpTbCmhTyPApoGJM6RIS/oKuOy67Q9Y6EMXTXyFMUoQJBAInTkqZQysaiAFKVszqXiTuBFNvMB93PXOioinjD34h6kGFv8wI45kWetArWruf2zmqQRS/++iekx/KShj3nvrECQQC8gote4141KbjC51m/a0KaN++qODHqupx55ZKgMwV985brg2r5s6xtTQmVrWJLXLlLMkyMmKkGOVH/sumHsJDp";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDJzPtqJ+Ob0VYDRmQlKs8PiF67jRFzroX5HZkUbDrB2MEyRpbZtMpMHBaLnyVDx0QvryMQ0z2FBMG17FXQn+2Wz3BU5fTp2zXEclbL0BP8HxN1lkLAJeI4qmcOV1eau4lsy1C8m3sKPbGJKdEb8JMaOIZS08wOaDjU4uZ9CL3OwIDAQAB";
//        RSA rsa = new RSA(privateKey, publicKey);

        // 要加密的原始字符串
        String plainText = "123456";

        Sign signature = new Sign(SignAlgorithm.SHA256withRSA, privateKey, publicKey);
        byte[] signData = signature.sign(plainText.getBytes("UTF-8"));
        String signBase64 = cn.hutool.core.codec.Base64.encode(signData);
        System.out.println("Signature: " + signBase64);

        // 使用公钥验证签名
        boolean isValid = signature.verify(plainText.getBytes("UTF-8"), signData);
        System.out.println("Is Valid Signature: " + isValid);

    }

}
