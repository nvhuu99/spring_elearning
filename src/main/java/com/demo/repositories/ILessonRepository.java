package com.demo.repositories;

import com.demo.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourseId(Long courseId);
}
