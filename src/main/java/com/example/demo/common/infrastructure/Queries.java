package com.example.demo.common.infrastructure;

import java.util.List;
import java.util.Optional;

public record Queries(List<Query> queryList) {

  public Optional<Query> getQueryByMethod(String methodName) {
    return queryList.stream()
        .filter(query -> query.isSameMethod(methodName))
        .findFirst();
  }

}
