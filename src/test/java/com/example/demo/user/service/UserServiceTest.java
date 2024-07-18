package com.example.demo.user.service;


import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.user.infrastructure.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

  @Autowired
  UserService userService;

  @Test
  @DisplayName("userService내의 Repository가 정상적으로 주입되었는지 확인한다")
  void checkRepositoryInjection(){
    String username = "hee";

    User user = userService.findUser(username);

    assertThat(user).isNotNull();
  }
}
