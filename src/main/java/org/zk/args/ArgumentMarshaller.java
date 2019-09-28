package org.zk.args;

import java.util.Iterator;

public abstract class ArgumentMarshaller {

    public abstract void set(Iterator<String> s);

    public abstract Object get();

}
