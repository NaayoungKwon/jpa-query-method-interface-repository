package com.example.demo.common.proxy;

import com.example.demo.common.infrastructure.MyJpaRepository;
import com.example.demo.common.infrastructure.Queries;
import com.example.demo.common.infrastructure.Query;
import com.example.demo.common.infrastructure.QueryMethodCache;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DynamicMyJpaHandler implements InvocationHandler {

  private  final QueryMethodCache queryMethodCache;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {

    if(Set.of(method.getDeclaringClass().getInterfaces()).contains(MyJpaRepository.class)){
      Class<? extends MyJpaRepository> declaringClass = (Class<? extends MyJpaRepository>) method.getDeclaringClass();
      Optional<Queries> queriesOptional = queryMethodCache.getCache(declaringClass);
      if(queriesOptional.isEmpty()){
        return null;
      }
      String result = queriesOptional.get().queryList().stream()
          .filter(query -> query.method().equals(method.getName()))
          .findFirst()
          .map(Query::query)
          .orElse(null);
      System.out.println(result);
      return null;
    }
    return null;
  }
}
