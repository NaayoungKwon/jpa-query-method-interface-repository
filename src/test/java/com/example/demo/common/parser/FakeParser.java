package com.example.demo.common.parser;

public class FakeParser implements Parser {


  @Override
  public String parse(String methodName, Class<?>[] parameterTypes) {
    return "select * from user";
  }
}
