package com.example.demo.common.infrastructure;

public record Query(String method, String query) {

  public boolean isSameMethod(String methodName){
    return method.equals(methodName);
  }
  public String mergeToParam(Object[] args){
    return String.format(query, args);
  }

}