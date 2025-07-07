package com.demo.repositories;

import com.demo.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICourseRepository extends JpaRepository<Course, Long> {
    @Query("""
        SELECT c FROM Course c
        WHERE (:categoryId IS NULL OR c.category.id = :categoryId)
          AND (:title IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%')))
    """)
    Page<Course> searchCourses(
        @Param("categoryId") Long categoryId,
        @Param("title") String title,
        Pageable pageable
    );

    @Query("""
        SELECT COUNT(c) FROM Course c
        WHERE (:categoryId IS NULL OR c.category.id = :categoryId)
          AND (:title IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%')))
    """)
    long countCourses(
        @Param("categoryId") Long categoryId,
        @Param("title") String title
    );

    List<Course> findByTitleContainingIgnoreCase(String keyword);
    List<Course> findByCategoryId(Long catId);
}
