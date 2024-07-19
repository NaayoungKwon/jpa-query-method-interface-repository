package com.example.demo.course.infrastructure;

import com.example.demo.common.infrastructure.MyJpaRepository;
import java.time.LocalDate;

public interface CourseRepository extends MyJpaRepository<Long, Course> {

  Course findByTitle(String title);

  Course findByOpenGreaterThan(LocalDate open);

}
