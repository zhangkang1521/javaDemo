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
    }

    @Test
    public void getBooleanUnexpected() {
        Args args = new Args("a", new String[]{"-a", "-b"});
        assertEquals(false, args.isValid());
    }

    @Test
    public void getStringNormal() {
        Args args = new Args("a*,b*,c*", new String[]{"-ab", "hello", "world", "-c", "great"});
        assertEquals(true, args.isValid());
        assertEquals("hello", args.getString('a'));
        assertEquals("world", args.getString('b'));
        assertEquals("great", args.getString('c'));
    }
}