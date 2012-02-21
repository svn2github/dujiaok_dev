package com.alibaba.antx.config;

public interface ConfigLogger {
    void debug(String message);

    void info(String message);

    void warn(String message);

    void error(String message);

    void error(Throwable cause);

    void error(String message, Throwable cause);
}
