package com.example.demo.common.infrastructure;

import com.example.demo.common.parser.Parser;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

@Component
public class QueryMethodCache {

  private final Parser parser;
  private final Map<Class<? extends MyJpaRepository>, Queries> queryCacheMap;

  public QueryMethodCache(Parser parser) {
    Set<Class<? extends MyJpaRepository>> subTypes = new Reflections(
        "com.example.demo").getSubTypesOf(MyJpaRepository.class);

    this.parser = parser;
    this.queryCacheMap = subTypes.stream()
        .collect(Collectors.toMap(subType -> subType, this::createQueries));
  }

  public  Map<Class<? extends MyJpaRepository>, Queries> getCaches(){
    return this.queryCacheMap;
  }

  public Optional<Queries> getCache(Class<? extends MyJpaRepository> clazz){
    return Optional.ofNullable(this.queryCacheMap.get(clazz));
  }

  private Queries createQueries(Class<? extends MyJpaRepository> clazz){
    List<Query> queryList = Arrays.stream(clazz.getMethods())
        .map(method -> createQuery(method.getName(), method.getReturnType().getSimpleName(), method.getParameterTypes())).toList();
    return new Queries(queryList);
  }

  private Query createQuery(String methodName, String tableName, Class<?>[] parameterTypes){
    String queryStr = parser.parse(methodName, tableName, parameterTypes);
    return new Query(methodName, queryStr);
  }

}
