package com.demo.services.drivers;

import com.demo.api.dto.CourseDTO;
import com.demo.exceptions.NotFoundException;
import com.demo.models.Course;
import com.demo.repositories.ICourseRepository;
import com.demo.services.ICategoryService;
import com.demo.services.ICourseService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class CourseService implements ICourseService {
    ICourseRepository courseRepo;
    ICategoryService catService;

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
    public boolean existsById(Long id) {
        return courseRepo.existsById(id);
    }

    @Override
    public Course save(CourseDTO data) throws Exception {
        // Get course
        Course course = new Course();
        if (data.getId() != null) {
            course = findById(data.getId());
            if (course == null) {
                throw new NotFoundException("Course ID", data.getId().toString());
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
