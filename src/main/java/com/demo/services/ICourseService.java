package com.demo.services;

import com.demo.api.dto.CourseDTO;
import com.demo.models.Course;

import java.util.List;

public interface ICourseService {
    List<Course> getAll();
    Course findById(Long id);
    List<Course> findByTitle(String keyword);
    List<Course> findByCategoryId(Long catId);
    boolean existsById(Long id);
    Course save(CourseDTO course) throws Exception;
    void deleteById(Long id) throws Exception;
}
