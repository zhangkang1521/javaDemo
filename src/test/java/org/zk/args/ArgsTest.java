package org.zk.args;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArgsTest {

    @Test
    public void getBooleanNormal() {
        Args args = new Args("a,b,c,d", new String[]{"-ab", "-c"});
        assertEquals(true, args.getBoolean('a'));
        assertEquals(true, args.getBoolean('b'));
        assertEquals(true, args.getBoolean('c'));
        assertEquals(false, args.getBoolean('d'));
        assertEquals(false, args.getBoolean('e'));
    }

    @Test
    public void getWrongType() {
        Args args = new Args("a,b*", new String[]{"-ab", "hello"});
        assertEquals(true, args.getBoolean('a'));
        assertEquals("hello", args.getString('b'));
        assertEquals(false, args.getBoolean('b'));
        assertEquals("", args.getString('a'));
    }

    @Test(expected = RuntimeException.class)
    public void getBooleanUnexpected() {
        Args args = new Args("a", new String[]{"-a", "-b"});
    }

    @Test
    public void getStringNormal() {
        Args args = new Args("a*,b*,c*", new String[]{"-ab", "hello", "world", "-c", "great"});
        assertEquals("hello", args.getString('a'));
        assertEquals("world", args.getString('b'));
        assertEquals("great", args.getString('c'));
        assertEquals("", args.getString('d'));
    }

    @Test
    public void getIntegerNormal() {
        Args args = new Args("a#", new String[]{"-a", "12"});
        assertEquals(12, args.getInteger('a'));
    }

    @Test
    public void getInvalidInteger() {
        Args args = null;
        try {
            new Args("a#", new String[]{"-a", "five"});
        } catch (ArgsException e) {
            assertEquals(ErrorCode.INVALID_INTEGER, e.getErrorCode());
            assertEquals("a期望是int，但是是five", e.getErrorMessage());
        }

    }

    @Test
    public void getMissingInteger() {
        try {
             new Args("a#", new String[]{"-a"});
             fail();
        } catch (ArgsException e) {
            assertEquals(ErrorCode.MISSING_INTEGER, e.getErrorCode());
            assertEquals("a缺失int参数", e.getErrorMessage());
        }
    }

    @Test(expected = RuntimeException.class)
    public void invalidSchema() {
        Args args = new Args("a!", new String[]{"-a", "hello"});
    }
}