package com.example.demo.common.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.common.infrastructure.QueryMethodCache;
import com.example.demo.common.parser.FakeParser;
import com.example.demo.user.infrastructure.User;
import com.example.demo.user.infrastructure.UserRepository;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DynamicMyJpaHandlerTest {

  @Test
  @DisplayName("Handler 등록이 되는지 확인")
  void checkHandlerPractice(){
    QueryMethodCache queryMethodCache = new QueryMethodCache(new FakeParser());
    DynamicMyJpaHandler handler = new DynamicMyJpaHandler(queryMethodCache);

    UserRepository myJpaRepository = (UserRepository) Proxy.newProxyInstance(
        UserRepository.class.getClassLoader(),
        new Class[]{UserRepository.class}, handler);

    User user = myJpaRepository.findByUsername("nayoung");
    assertThat(user).isNotNull();
  }
}
