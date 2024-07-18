package com.example.demo.common.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QueryTest {

  @Test
  @DisplayName("Query의 메서드 명과 동일한지 확인한다")
  void checkSameMethodName(){
    Query query = new Query("findByUsername", "SELECT * FROM user WHERE username = '%s'");
    boolean result = query.isSameMethod("findByUsername");

    assertThat(result).isTrue();
  }

  @Test
  @DisplayName("Query의 메서드 명과 일지하지 않으면 false 반환")
  void checkDifferentMethodName(){
    Query query = new Query("findByUsername", "SELECT * FROM user WHERE username = '%s'");
    boolean result = query.isSameMethod("findByEmail");

    assertThat(result).isFalse();
  }

  @Test
  @DisplayName("Query의 where절에 파라미터를 넣어 반환한다")
  void checkMergeToParam(){
    Query query = new Query("findByUsernameAndEmail", "SELECT * FROM user WHERE username = %s and email = %s");
    String result = query.mergeToParam(new Object[]{"nayoung", "nayoung@test.com"});

    assertThat(result).isEqualTo("SELECT * FROM user WHERE username = nayoung and email = nayoung@test.com");
  }

}
