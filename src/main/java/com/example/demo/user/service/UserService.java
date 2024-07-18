package com.example.demo.user.service;

import com.example.demo.user.infrastructure.User;
import com.example.demo.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User findUser(String username) {
    return userRepository.findByUsername(username);
  }
}
