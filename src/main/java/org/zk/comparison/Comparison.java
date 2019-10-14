package org.zk.comparison;

public class Comparison {
    private int contextLength;
    private String expectStr;
    private String actualStr;
    private int prefixLength = 0;
    private int suffixLength = 0;

    public Comparison(int contextLength, String expectStr, String actualStr) {
        this.contextLength = contextLength;
        this.expectStr = expectStr;
        this.actualStr = actualStr;
    }

    public String compact(String msg) {
        if (expectStr == null || actualStr == null || expectStr.equals(actualStr)) {
            return Assert.format(msg, expectStr, actualStr);
        }
        findPrefixLength();
        findSuffixLength();
        String compactExpect = compactString(expectStr);
        String actualExpect = compactString(actualStr);
        return Assert.format(msg, compactExpect, actualExpect);
    }

    private void findPrefixLength() {
        for (int i = 0, j = 0; i < expectStr.length() && j < actualStr.length(); i++, j++) {
            if (expectStr.charAt(i) != actualStr.charAt(j)) {
                break;
            }
            prefixLength++;
        }
    }

    private void findSuffixLength() {
        for (int i = expectStr.length() - 1, j = actualStr.length() - 1; i >= prefixLength && j >= prefixLength; i--, j--) {
            if (expectStr.charAt(i) != actualStr.charAt(j)) {
                break;
            }
            suffixLength++;
        }
    }

    private String compactString(String str) {
        StringBuilder compactStr = new StringBuilder();
        compactStr.append(startContext());
        compactStr.append("[");
        compactStr.append(delta(str));
        compactStr.append("]");
        compactStr.append(endContext());
        return compactStr.toString();
    }

    private String startContext() {
        String samePrefix = expectStr.substring(0, prefixLength);
        return samePrefix.length() > contextLength ? "..." + samePrefix.substring(prefixLength - contextLength) : samePrefix;
    }

    private String delta(String str) {
        return str.substring(prefixLength, str.length() - suffixLength);
    }

    private String endContext() {
        String sameSuffix = expectStr.substring(expectStr.length() - suffixLength);
        return sameSuffix.length() > contextLength ? sameSuffix.substring(0, contextLength) + "..." : sameSuffix;
    }


}
