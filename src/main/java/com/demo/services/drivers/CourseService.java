package com.demo.services.drivers;

import com.demo.services.dto.course.SaveCourse;
import com.demo.exceptions.NotFoundException;
import com.demo.models.Course;
import com.demo.repositories.ICourseRepository;
import com.demo.services.ICategoryService;
import com.demo.services.ICourseService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class CourseService implements ICourseService {
    final ICourseRepository courseRepo;
    final ICategoryService catService;

    @Override
    public List<Course> getAll() {
        return courseRepo.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courseRepo.findById(id).orElse(null);
    }

    @Override
    public List<Course> findByTitle(String keyword) {
        return courseRepo.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public List<Course> findByCategoryId(Long catId) {
        return courseRepo.findByCategoryId(catId);
    }

    @Override
    public List<Course> searchCourses(Long catId, String name, Integer page, Integer size) {
        var pagination = PageRequest.of(page, size);
        return courseRepo.searchCourses(catId, name, pagination).getContent();
    }

    @Override
    public Long countCourses(Long catId, String name) {
        return courseRepo.countCourses(catId, name);
    }

    @Override
    public boolean existsById(Long id) {
        return courseRepo.existsById(id);
    }

    @Override
    public Course save(Long id, SaveCourse data) throws Exception {
        // Get course
        Course course = new Course();
        if (id != null) {
            course = findById(id);
            if (course == null) {
                throw new NotFoundException("Course ID", id.toString());
            }
        }
        // Get category
        var category = catService.findById(data.getCategoryId());
        if (category == null) {
            throw new NotFoundException("Category ID", data.getCategoryId().toString());
        }
        // Set data
        course.setTitle(data.getTitle());
        course.setDays(data.getDays());
        course.setHours(data.getHours());
        course.setCategory(category);
        // Save
        return courseRepo.save(course);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        if (! existsById(id)) {
            throw new NotFoundException("Course ID", id.toString());
        }
        courseRepo.deleteById(id);
    }
}
