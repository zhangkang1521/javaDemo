package org.zk;

import cn.hutool.crypto.digest.DigestUtil;
import org.junit.Test;

/**
 * @author zhangkang
 * @date 2025/1/18 20:49
 */
public class DigestUtilTest {

    @Test
    public void hash() {
        // 不推荐使用
        System.out.println(DigestUtil.md5Hex("123456"));
        // 推荐使用
        System.out.println(DigestUtil.sha512Hex("123456"));
    }
}
