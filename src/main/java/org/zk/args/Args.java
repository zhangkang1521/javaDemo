package org.zk.args;

import java.util.*;

public class Args {
    private String schema;
    List<String> argList;
    private boolean valid;
    private Set<Character> unexpectedArguments = new TreeSet<>();

    private Map<Character, ArgumentMarshaller> marshallers = new HashMap<>();

    Iterator<String> currentArgument;

    public Args(String schema, String[] args) {
        this.schema = schema;
        this.argList = Arrays.asList(args);
        valid = parse();
    }

    private boolean parse() {
        parseSchema();
        parseArgument();
        return unexpectedArguments.size() == 0;
    }

    private void parseSchema() {
        for (String element : schema.split(",")) {
            parseSchemaElement(element);
        }
    }

    private void parseSchemaElement(String element) {
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        if (isBooleanSchemaElement(elementTail)) {
            marshallers.put(elementId, new BooleanArgumentMarshaller());
        } else if (isStringSchemaElement(elementTail)) {
            marshallers.put(elementId, new StringArgumentMarshaller());
        } else {
            throw new RuntimeException("invalid schema, elementId:" + elementId + ",elementTail:" + elementTail);
        }
    }

    private boolean isBooleanSchemaElement(String elementTail) {
        return elementTail.length() == 0;
    }

    private boolean isStringSchemaElement(String elementTail) {
        return "*".equals(elementTail);
    }


    private void parseArgument() {
        for(currentArgument = argList.iterator(); currentArgument.hasNext(); ) {
            parseArgument(currentArgument.next());
        }
    }

    private void parseArgument(String arg) {
        if (arg.startsWith("-")) {
            parseElements(arg);
        }
    }

    private void parseElements(String arg) {
        for (int i = 1; i < arg.length(); i++) {
            parseElement(arg.charAt(i));
        }
    }

    private void parseElement(char argChar) {
        ArgumentMarshaller m = marshallers.get(argChar);
        m.set(currentArgument);
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

    public boolean isValid() {
        return valid;
    }

}

