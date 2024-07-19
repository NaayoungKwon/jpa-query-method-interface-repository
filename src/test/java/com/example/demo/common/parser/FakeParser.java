package com.example.demo.common.parser;

public class FakeParser implements Parser {


  @Override
  public String parse(String methodName, String tableName, Class<?>[] parameterTypes) {
    return "select * from user where name = ? and email = ?";
  }
}
