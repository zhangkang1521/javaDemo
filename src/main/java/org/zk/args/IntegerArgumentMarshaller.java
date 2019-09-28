package org.zk.args;

import java.util.Iterator;

public class IntegerArgumentMarshaller extends ArgumentMarshaller{

    private int intValue = 0;

    @Override
    public void set(Iterator<String> s) {
        intValue = Integer.parseInt(s.next());
    }

    @Override
    public Object get() {
        return intValue;
    }
}
