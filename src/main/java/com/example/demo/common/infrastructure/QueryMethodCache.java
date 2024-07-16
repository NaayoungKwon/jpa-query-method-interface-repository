package com.example.demo.common.infrastructure;

import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

@Component
public class QueryMethodCache {

  private final Map<Class<? extends MyJpaRepository>, Queries> queryCacheMap;

  public QueryMethodCache() {
    Set<Class<? extends MyJpaRepository>> subTypes = new Reflections(
        "com.example.demo").getSubTypesOf(MyJpaRepository.class);
    this.queryCacheMap = null;
  }

  public  Map<Class<? extends MyJpaRepository>, Queries> getCaches(){
    return null;
  }

  public Queries getCache(Class<? extends MyJpaRepository> clazz){
    return null;
  }

}
