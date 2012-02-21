package com.alibaba.antx.util.i18n;


public class UnsupportedLocaleException extends Exception {
    private static final long serialVersionUID = 3258133574254671669L;

    public UnsupportedLocaleException() {
        super();
    }

    public UnsupportedLocaleException(String message) {
        super(message);
    }

    public UnsupportedLocaleException(Throwable cause) {
        super(cause);
    }

    public UnsupportedLocaleException(String message, Throwable cause) {
        super(message, cause);
    }
}
