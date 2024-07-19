package com.example.demo.user.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Test
  @DisplayName("findByEmailAndAgeGreaterThan 동작 확인")
  void findByEmailAndAgeGreaterThan() {
    String email = "nayoung@test.com";
    Integer age = 20;

    User user = userRepository.findByEmailAndAgeGreaterThan(email, age);

    assertThat(user).isNotNull();
  }

  @Test
  @DisplayName("findByNameStartingWith 동작 확인")
  void findByNameStartingWith() {
    String name = "hyeon";

    User user = userRepository.findByNameStartingWith(name);

    assertThat(user).isNotNull();
  }

}


