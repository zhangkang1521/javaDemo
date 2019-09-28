package org.zk.args;

public class ArgsException extends RuntimeException {

    private ErrorCode errorCode;
    private char errorArgumentId;
    private String errorParam;

    public ArgsException(ErrorCode errorCode, char errorArgumentId, String errorParam) {
        this.errorCode = errorCode;
        this.errorArgumentId = errorArgumentId;
        this.errorParam = errorParam;
    }

    public ArgsException(ErrorCode errorCode, String errorParam) {
        this.errorCode = errorCode;
        this.errorParam = errorParam;
    }

    public ArgsException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public char getErrorArgumentId() {
        return errorArgumentId;
    }

    public void setErrorArgumentId(char errorArgumentId) {
        this.errorArgumentId = errorArgumentId;
    }

    public String getErrorParam() {
        return errorParam;
    }

    public void setErrorParam(String errorParam) {
        this.errorParam = errorParam;
    }

    public String getErrorMessage() {
        switch (errorCode) {
            case MISSING_INTEGER: return String.format("%c缺失int参数", errorArgumentId);
            case INVALID_INTEGER: return String.format("%c期望是int，但是是%s", errorArgumentId, errorParam);
            default: return "未知错误";
        }
    }
}
