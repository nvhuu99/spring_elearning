package com.demo.repositories;

import com.demo.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTitleContainingIgnoreCase(String keyword);
    List<Course> findByCategoryId(Long catId);
}
