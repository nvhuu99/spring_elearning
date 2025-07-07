package com.demo.services;

import com.demo.services.dto.course.SaveCourse;
import com.demo.models.Course;

import java.util.List;

public interface ICourseService {
    List<Course> getAll();
    Course findById(Long id);

    List<Course> searchCourses(Long catId, String keyword, Integer page, Integer size);
    Long countCourses(Long catId, String keyword);

    List<Course> findByTitle(String keyword);
    List<Course> findByCategoryId(Long catId);

    boolean existsById(Long id);
    Course save(Long id, SaveCourse course) throws Exception;
    void deleteById(Long id) throws Exception;
}
