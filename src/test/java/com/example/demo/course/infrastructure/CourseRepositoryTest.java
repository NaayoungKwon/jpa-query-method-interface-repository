package com.example.demo.course.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;

    @Test
    @DisplayName("findByTitle 동작 확인")
    void findByTitle() {
      String title = "boost camp";

      Course course = courseRepository.findByTitle(title);

      assertThat(course).isNotNull();
    }

    @Test
    @DisplayName("findByOpenGreaterThan 동작 확인")
    void findByOpenGreaterThan() {
      LocalDate open = LocalDate.now();

      Course course = courseRepository.findByOpenGreaterThan(open);

      assertThat(course).isNotNull();
    }

}
