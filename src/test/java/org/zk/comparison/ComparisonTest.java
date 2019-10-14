package org.zk.comparison;

import org.junit.Assert;
import org.junit.Test;

public class ComparisonTest {

    @Test
    public void compactEqual() {
        String str = new Comparison(1, "a", "a").compact(null);
        Assert.assertEquals("expected:<a> but was:<a>", str);
    }

    @Test
    public void compactNull() {
        String str = new Comparison(1, null, "a").compact(null);
        Assert.assertEquals("expected:<null> but was:<a>", str);
        String str2 = new Comparison(1, "a", null).compact(null);
        Assert.assertEquals("expected:<a> but was:<null>", str2);
    }

    @Test
    public void compact() {
        String str = new Comparison(1, "abc", "axc").compact(null);
        Assert.assertEquals("expected:<a[b]c> but was:<a[x]c>", str);
    }

    @Test
    public void compactStartSame() {
        String str = new Comparison(10, "abc", "abx").compact(null);
        Assert.assertEquals("expected:<ab[c]> but was:<ab[x]>", str);
    }

    @Test
    public void compactEndSame() {
        String str = new Comparison(10, "abc", "xxbc").compact(null);
        Assert.assertEquals("expected:<[a]bc> but was:<[xx]bc>", str);
    }

    @Test
    public void compact1() {
        String str = new Comparison(1, "a", "aa").compact(null);
        Assert.assertEquals("expected:<a[]> but was:<a[a]>", str);
    }

    @Test
    public void compact2() {
        // 从后面开始比较也有相同，但需保证不能检测前面已经相同的部分
        String str = new Comparison(1, "aa", "a").compact(null);
        Assert.assertEquals("expected:<a[a]> but was:<a[]>", str);
    }

    @Test
    public void compactContextLength() {
        String str = new Comparison(1, "abcde", "abxde").compact(null);
        Assert.assertEquals("expected:<...b[c]d...> but was:<...b[x]d...>", str);
    }

    @Test
    public void compactContextLength0() {
        String str = new Comparison(0, "abcde", "abxde").compact(null);
        Assert.assertEquals("expected:<...[c]...> but was:<...[x]...>", str);
    }

    @Test
    public void compactContextxx() {
        String str = new Comparison(10, "S&P500", "0").compact(null);
        Assert.assertEquals("expected:<[S&P50]0> but was:<[]0>", str);
    }

}