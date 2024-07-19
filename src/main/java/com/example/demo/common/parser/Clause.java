package com.example.demo.common.parser;

public record Clause(CombineKeyword token, Operator operator, String column) {

  public boolean isNotFirst(){
    return token != CombineKeyword.FIRST;
  }

  public String toQueryString(){
   return " " + column.toLowerCase() + " " + operator.getQueryKeyword();
  }
}
