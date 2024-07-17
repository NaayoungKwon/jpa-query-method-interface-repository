package com.example.demo.common.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.common.parser.FakeParser;
import com.example.demo.user.infrastructure.UserRepository;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QueryMethodCacheTest {

  QueryMethodCache queryMethodCache;

  @BeforeEach
  void init() {
    queryMethodCache = new QueryMethodCache(new FakeParser());
  }

  @Test
  @DisplayName("MyJpaRepository를 상속하는 Interface를 확인한다.")
  void checkRepositoryScan(){
    Map<Class<? extends MyJpaRepository>, Queries> cache = queryMethodCache.getCaches();

    assertThat(cache).hasSize(2);

  }

  @Test
  @DisplayName("UserRepository에 정의한 메소드들이 있는지 확인한다.")
  void checkUserRepositoryMethods(){
    Queries queries = queryMethodCache.getCache(UserRepository.class).get();

    assertThat(queries.queryList()).isNotEmpty();
    assertThat(queries.queryList().get(0).query()).isEqualTo("select * from user where username = %s");
  }

}
