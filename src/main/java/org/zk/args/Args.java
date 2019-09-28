package org.zk.args;

import java.util.*;

public class Args {
    private Map<Character, ArgumentMarshaller> marshallers = new HashMap<>();
    private Iterator<String> currentArgument;

    public Args(String schema, String[] args) {
        parseSchema(schema);
        parseArgument(Arrays.asList(args));
    }

    private void parseSchema(String schema) {
        for (String element : schema.split(",")) {
            parseSchemaElement(element);
        }
    }

    private void parseSchemaElement(String element) {
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        if (elementTail.length() == 0) {
            marshallers.put(elementId, new BooleanArgumentMarshaller());
        } else if ("*".equals(elementTail)) {
            marshallers.put(elementId, new StringArgumentMarshaller());
        } else if ("#".equals(elementTail)) {
            marshallers.put(elementId, new IntegerArgumentMarshaller());
        }else {
            throw new RuntimeException("invalid schema, elementId:" + elementId + ",elementTail:" + elementTail);
        }
    }



    private void parseArgument(List<String> argList) {
        for(currentArgument = argList.iterator(); currentArgument.hasNext(); ) {
            String arg = currentArgument.next();
            if (arg.startsWith("-")) {
                parseElements(arg);
            }
        }
    }


    private void parseElements(String arg) {
        for (int i = 1; i < arg.length(); i++) {
            parseElement(arg.charAt(i));
        }
    }

    private void parseElement(char argChar) {
        ArgumentMarshaller m = marshallers.get(argChar);
        if (m == null) {
            throw new RuntimeException("参数" + argChar + "未在schema中找到");
        }
        try {
            m.set(currentArgument);
        } catch (ArgsException e) {
            e.setErrorArgumentId(argChar);
            throw e;
        }
    }



    public boolean getBoolean(char arg) {
        try {
            ArgumentMarshaller am = marshallers.get(arg);
            return am != null && (boolean)am.get();
        } catch (ClassCastException e) {
            return false;
        }
    }

    public String getString(char arg) {
        try {
            ArgumentMarshaller am = marshallers.get(arg);
            return am == null ? "" : (String)am.get();
        } catch (ClassCastException e) {
            return "";
        }
    }

    public int getInteger(char arg) {
        try {
            ArgumentMarshaller am = marshallers.get(arg);
            return am == null ? 0 : (Integer)am.get();
        } catch (ClassCastException e) {
            return 0;
        }
    }


}

