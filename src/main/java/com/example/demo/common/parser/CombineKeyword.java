package com.example.demo.common.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CombineKeyword {
  AND("And"),
  OR("Or"),
  FIRST ("First");

  private final String methodKeyword;

  public static CombineKeyword find(String methodKeyword) {
    for (CombineKeyword combineKeyword : CombineKeyword.values()) {
      if (combineKeyword.getMethodKeyword().equals(methodKeyword)) {
        return combineKeyword;
      }
    }
    return FIRST;
  }
}
