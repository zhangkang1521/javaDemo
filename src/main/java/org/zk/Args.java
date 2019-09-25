package org.zk;

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
    private int numberOfArguments = 0;

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
        if (element.length() == 1) {
            parseBooleanSchema(element);
        }
    }

    private void parseBooleanSchema(String element) {
        char ch = element.charAt(0);
        if (Character.isLetter(ch)) {
            booleanArgs.put(ch, false);
        }
    }

    private void parseArgument() {
        for(String arg : args) {
            parseArgument(arg);
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
            numberOfArguments++;
            setBooleanArg(argChar, true);
        } else {
            unexpectedArguments.add(argChar);
        }
    }

    private void setBooleanArg(char argChar, boolean value) {
        booleanArgs.put(argChar, value);
    }

    private boolean isBoolean(char argChar) {
        return booleanArgs.containsKey(argChar);
    }

    public boolean getBoolean(char arg) {
        return booleanArgs.get(arg);
    }

    public boolean isValid() {
        return valid;
    }


}
