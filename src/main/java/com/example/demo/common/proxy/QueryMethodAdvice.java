package com.example.demo.common.proxy;


import com.example.demo.common.infrastructure.MyJpaRepository;
import com.example.demo.common.infrastructure.Queries;
import com.example.demo.common.infrastructure.Query;
import com.example.demo.common.infrastructure.QueryMethodCache;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
@AllArgsConstructor
public class QueryMethodAdvice implements MethodInterceptor {

  private  final QueryMethodCache queryMethodCache;

  @Nullable
  @Override
  public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
    Method method = invocation.getMethod();
    if(isNotMyJpaRepository(method)){
      return null;
    }

    String finalQuery = getQueriesFromRepository(method)
        .map(query -> query.mergeToParam(invocation.getArguments()))
        .orElse(null);
    log.info("query : {}", finalQuery);

    return method.getReturnType().getConstructor().newInstance();
  }

  private Optional<Query> getQueriesFromRepository(Method method) {
    Class<? extends MyJpaRepository> declaringClass = (Class<? extends MyJpaRepository>) method.getDeclaringClass();

    Queries queries = queryMethodCache.getCache(declaringClass).orElseThrow(() -> {
      log.error("Scan 되지 않은 Class입니다. class : {}", declaringClass.getName());
      return new RuntimeException("Scan 되지 않은 Class입니다. class : " + declaringClass.getName());
    });

     return queries.getQueryByMethod(method.getName());
  }

  private boolean isNotMyJpaRepository(Method method){
    return !Set.of(method.getDeclaringClass().getInterfaces()).contains(MyJpaRepository.class);
  }


}
