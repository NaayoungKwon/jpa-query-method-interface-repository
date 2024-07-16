package com.example.demo.common.infrastructure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

@Component
public class QueryMethodCache {

  private final Map<Class<? extends MyJpaRepository>, Queries> queryCacheMap;

  public QueryMethodCache() {
    Set<Class<? extends MyJpaRepository>> subTypes = new Reflections(
        "com.example.demo").getSubTypesOf(MyJpaRepository.class);

    this.queryCacheMap = subTypes.stream()
        .collect(Collectors.toMap(subType -> subType, this::createQueries));
  }

  public  Map<Class<? extends MyJpaRepository>, Queries> getCaches(){
    return this.queryCacheMap;
  }

  public Queries getCache(Class<? extends MyJpaRepository> clazz){
    return this.queryCacheMap.get(clazz);
  }

  private Queries createQueries(Class<? extends MyJpaRepository> clazz){
    List<Query> queryList = Arrays.stream(clazz.getMethods())
        .map(method -> createQuery(method.getName())).toList();
    return new Queries(queryList);
  }

  private Query createQuery(String methodName){
    return new Query(methodName, "");
  }

}
