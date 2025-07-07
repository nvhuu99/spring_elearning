package com.demo.services;

import com.demo.services.dto.lesson.SaveLesson;
import com.demo.models.Lesson;

import java.util.List;

public interface ILessonService {
    List<Lesson> getAll();
    Lesson findById(Long id);
    List<Lesson> findByCourseId(Long courseId);
    boolean existsById(Long id);
    Lesson save(Long id, SaveLesson data) throws Exception;
    void deleteById(Long id) throws Exception;
}
