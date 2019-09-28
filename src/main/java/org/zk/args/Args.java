package org.zk.args;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Args {
    private String schema;
    private String[] args;
    private boolean valid;
    private Set<Character> unexpectedArguments = new TreeSet<>();

    private Map<Character, Boolean> booleanArgs = new HashMap<>();
    private Map<Character, String> stringArgs = new HashMap<>();

    private int currentArgument = 0;

    public Args(String schema, String[] args) {
        this.schema = schema;
        this.args = args;
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
            parseBooleanSchema(elementId);
        } else if (isStringSchemaElement(elementTail)) {
            parseStringSchema(elementId);
        }
    }

    private boolean isBooleanSchemaElement(String elementTail) {
        return elementTail.length() == 0;
    }

    private boolean isStringSchemaElement(String elementTail) {
        return "*".equals(elementTail);
    }

    private void parseBooleanSchema(char elementId) {
        booleanArgs.put(elementId, false);
    }

    private void parseStringSchema(char elementId) {
        stringArgs.put(elementId, "");
    }


    private void parseArgument() {
        for(currentArgument = 0; currentArgument < args.length; currentArgument++) {
            parseArgument(args[currentArgument]);
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
        if (isBoolean(argChar)) {
            setBooleanArg(argChar, true);
        } else if(isString(argChar)) {
            setStringArg(argChar);
        } else {
            unexpectedArguments.add(argChar);
        }
    }

    private void setBooleanArg(char argChar, boolean value) {
        booleanArgs.put(argChar, value);
    }

    private void setStringArg(char argChar) {
        stringArgs.put(argChar, args[++currentArgument]);
    }

    private boolean isBoolean(char argChar) {
        return booleanArgs.containsKey(argChar);
    }

    private boolean isString(char argChar) {
        return stringArgs.containsKey(argChar);
    }

    public boolean getBoolean(char arg) {
        return booleanArgs.get(arg);
    }

    public String getString(char arg) {
        return stringArgs.get(arg);
    }

    public boolean isValid() {
        return valid;
    }


}
