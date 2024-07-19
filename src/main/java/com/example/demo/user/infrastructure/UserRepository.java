package com.example.demo.user.infrastructure;

import com.example.demo.common.infrastructure.MyJpaRepository;

public interface UserRepository extends MyJpaRepository<Long, User> {

  User findByUsername(String username);

  User findByEmailAndAgeGreaterThan(String email, Integer age);

  User findByNameStartingWith(String name);

}
