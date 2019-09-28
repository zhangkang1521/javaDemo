package org.zk.args;

import java.util.Iterator;

public class StringArgumentMarshaller extends ArgumentMarshaller{

    private String stringValue = "";

    @Override
    public void set(Iterator<String> current) {
        stringValue = current.next();
    }

    @Override
    public Object get() {
        return stringValue == null ? "" : stringValue;
    }
}
