package org.zk;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.symmetric.DES;
import lombok.SneakyThrows;
import org.junit.Test;

/**
 * @author zhangkang
 * @date 2025/1/18 20:58
 */
public class DesTest {

    @Test
    @SneakyThrows
    public void test() {
        // 可以使用AES加密
        // 定义一个 DES 密钥（必须是 8 字节）
        String key = "12345678"; // 注意：实际应用中应使用更安全的方式生成密钥

        // 创建 DES 实例
        DES des = new DES(key.getBytes("UTF-8"));

        // 要加密的原始字符串
        String plainText = "123456";

        // 加密操作
        byte[] encryptData = des.encrypt(plainText);
        String base64EncryptedData = Base64.encode(encryptData);
        System.out.println("Encrypted: " + base64EncryptedData);

        // 解密操作
        byte[] decryptData = des.decrypt(Base64.decode(base64EncryptedData));
        String decryptedText = new String(decryptData, "UTF-8");
        System.out.println("Decrypted: " + decryptedText);
    }
}
