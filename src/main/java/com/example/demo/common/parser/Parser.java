package com.example.demo.common.parser;

public interface Parser {
    String parse(String methodName, String tableName, Class<?>[] parameterTypes);

}
