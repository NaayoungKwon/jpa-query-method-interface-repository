package com.example.demo.common.infrastructure;


import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.course.infrastructure.CourseRepository;
import com.example.demo.user.infrastructure.UserRepository;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

class MyJpaRepositoryTest {

  @Test
  @DisplayName("MyJpaRepository를 상속하는 Interface를 확인한다.")
  void checkExtendsInterface() {
    Set<Class<? extends MyJpaRepository>> subTypes = new Reflections(
        "com.example.demo").getSubTypesOf(MyJpaRepository.class);

    assertThat(subTypes).hasSize(2);
    assertThat(subTypes).contains(UserRepository.class, CourseRepository.class);
  }
}
