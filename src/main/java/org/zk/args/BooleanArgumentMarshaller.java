package org.zk.args;

import java.util.Iterator;

public class BooleanArgumentMarshaller extends ArgumentMarshaller {

    private boolean booleanValue = false;

    @Override
    public void set(Iterator<String> current) {
        booleanValue = true;
    }

    @Override
    public Object get() {
        return booleanValue;
    }
}
