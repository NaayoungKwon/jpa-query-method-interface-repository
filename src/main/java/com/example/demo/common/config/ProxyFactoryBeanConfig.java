package com.example.demo.common.config;

import com.example.demo.common.infrastructure.MyJpaRepository;
import com.example.demo.common.infrastructure.QueryMethodCache;
import com.example.demo.common.parser.MethodParser;
import com.example.demo.common.proxy.QueryMethodAdvice;
import org.reflections.Reflections;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyFactoryBeanConfig {

  @Bean
  public ProxyFactoryBean proxyFactoryBean() throws ClassNotFoundException {
    QueryMethodCache queryMethodCache = new QueryMethodCache(new MethodParser());
    ProxyFactoryBean pfBean = new ProxyFactoryBean();

    Class[] array = new Reflections("com.example.demo").getSubTypesOf(MyJpaRepository.class).toArray(new Class[0]);
    pfBean.setProxyInterfaces(array);
    pfBean.addAdvice(new QueryMethodAdvice(queryMethodCache));
    return pfBean;
  }
}
