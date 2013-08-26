package com.tengen.XmlParser;

/**
 * User: pgajjar Date: 8/23/13 Time: 12:08 PM
 */
@SuppressWarnings("unused")
public class XmlParseException extends Exception {
    public enum XmlParseErrorCode {
        XML_PARSING_ERROR,
        XML_FILE_IO_ERROR
    }

    private final XmlParseErrorCode errorCode;
    private final String errorMsg;
    private final Exception actualException;

    public XmlParseException(XmlParseErrorCode errorCode, String errorMsg, Exception actualException) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.actualException = actualException;
    }

    public XmlParseErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Exception getActualException() {
        return actualException;
    }
}
