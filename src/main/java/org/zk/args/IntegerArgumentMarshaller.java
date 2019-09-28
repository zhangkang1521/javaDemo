package org.zk.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntegerArgumentMarshaller extends ArgumentMarshaller{

    private int intValue = 0;

    @Override
    public void set(Iterator<String> s) {
        String param = null;
        try {
            param = s.next();
            intValue = Integer.parseInt(param);
        } catch (NoSuchElementException e) {
            throw new ArgsException(ErrorCode.MISSING_INTEGER);
        } catch (NumberFormatException e) {
            throw new ArgsException(ErrorCode.INVALID_INTEGER, param);
        }
    }

    @Override
    public Object get() {
        return intValue;
    }
}
