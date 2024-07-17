package com.example.demo.common.proxy;

import com.example.demo.common.infrastructure.MyJpaRepository;
import com.example.demo.common.infrastructure.Queries;
import com.example.demo.common.infrastructure.Query;
import com.example.demo.common.infrastructure.QueryMethodCache;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class DynamicMyJpaHandler implements InvocationHandler {

  private  final QueryMethodCache queryMethodCache;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

    if(isNotMyJpaRepository(method)){
      return null;
    }

      Class<? extends MyJpaRepository> declaringClass = (Class<? extends MyJpaRepository>) method.getDeclaringClass();
      Optional<Queries> queriesOptional = queryMethodCache.getCache(declaringClass);
      if(queriesOptional.isEmpty()){
        log.error("Scan 되지 않은 Class입니다. class : {}", declaringClass.getName());
        return null;
      }

      String finalQuery = queriesOptional.get().queryList().stream()
          .filter(query -> query.method().equals(method.getName()))
          .findFirst()
          .map(Query::query)
          .map(query -> mergeParams(query, args))
          .orElse(null);
      log.info("query : {}", finalQuery);

      return method.getReturnType().getConstructor().newInstance();
  }

  private boolean isNotMyJpaRepository(Method method){
    return !Set.of(method.getDeclaringClass().getInterfaces()).contains(MyJpaRepository.class);
  }

  private String mergeParams(String rawQuery, Object[] args){
    return String.format(rawQuery, args);
  }
}
