package org.zk.args;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArgsTest {

    @Test
    public void getBooleanNormal() {
        Args args = new Args("a,b,c,d", new String[]{"-ab", "-c"});
        assertEquals(true, args.isValid());
        assertEquals(true, args.getBoolean('a'));
        assertEquals(true, args.getBoolean('b'));
        assertEquals(true, args.getBoolean('c'));
        assertEquals(false, args.getBoolean('d'));
        assertEquals(false, args.getBoolean('e'));
    }

    @Test
    public void getWrongType() {
        Args args = new Args("a,b*", new String[]{"-ab", "hello"});
        assertEquals(true, args.isValid());
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
        assertEquals(true, args.isValid());
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

    @Test(expected = RuntimeException.class)
    public void invalidSchema() {
        Args args = new Args("a!", new String[]{"-a", "hello"});
    }
}