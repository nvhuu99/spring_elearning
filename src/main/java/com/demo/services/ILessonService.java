package com.demo.services;

import com.demo.api.dto.CourseDTO;
import com.demo.api.dto.LessonDTO;
import com.demo.models.Course;
import com.demo.models.Lesson;

import java.util.List;

public interface ILessonService {
    List<Lesson> getAll();
    Lesson findById(Long id);
    List<Lesson> findByCourseId(Long courseId);
    boolean existsById(Long id);
    Lesson save(LessonDTO data) throws Exception;
    void deleteById(Long id) throws Exception;
}
