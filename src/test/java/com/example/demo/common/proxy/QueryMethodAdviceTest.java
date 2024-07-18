package com.example.demo.common.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.common.infrastructure.MyJpaRepository;
import com.example.demo.common.infrastructure.QueryMethodCache;
import com.example.demo.common.parser.FakeParser;
import com.example.demo.user.infrastructure.User;
import com.example.demo.user.infrastructure.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.aop.framework.ProxyFactoryBean;

public class QueryMethodAdviceTest {

  @Test
  @DisplayName("Advice가 동작하는지 테스트를 한다")
  void checkAdvice() throws ClassNotFoundException {
    QueryMethodCache queryMethodCache = new QueryMethodCache(new FakeParser());
    ProxyFactoryBean pfBean = new ProxyFactoryBean();

    Class[] array = new Reflections("com.example.demo").getSubTypesOf(MyJpaRepository.class).toArray(new Class[0]);
    pfBean.setProxyInterfaces(array);
    pfBean.addAdvice(new QueryMethodAdvice(queryMethodCache));

    UserRepository userRepository = (UserRepository) pfBean.getObject();
    User user = userRepository.findByUsername("nayoung");
    assertThat(user).isNotNull();

  }

}
