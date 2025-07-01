package com.demo.services.drivers;

import com.demo.api.dto.LessonDTO;
import com.demo.exceptions.NotFoundException;
import com.demo.models.Lesson;
import com.demo.repositories.ICourseRepository;
import com.demo.repositories.ILessonRepository;
import com.demo.services.ILessonService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class LessonService implements ILessonService {
    ICourseRepository courseRepo;
    ILessonRepository lessonRepo;

    @Override
    public List<Lesson> getAll() {
        return lessonRepo.findAll();
    }

    @Override
    public Lesson findById(Long id) {
        return lessonRepo.findById(id).orElse(null);
    }

    @Override
    public List<Lesson> findByCourseId(Long courseId) {
        return lessonRepo.findByCourseId(courseId);
    }

    @Override
    public boolean existsById(Long id) {
        return lessonRepo.existsById(id);
    }

    @Override
    public Lesson save(LessonDTO data) throws Exception {
        // Get lesson
        Lesson lesson = new Lesson();
        if (data.getId() != null) {
            lesson = findById(data.getId());
            if (lesson == null) {
                throw new NotFoundException("Lesson ID", data.getId().toString());
            }
        }
        // Get course
        var course = courseRepo.findById(data.getCourseId());
        if (course.isEmpty()) {
            throw new NotFoundException("Course ID", data.getCourseId().toString());
        }
        // Set data
        lesson.setHours(data.getHours());
        lesson.setName(data.getName());
        lesson.setCourse(course.get());
        // Save
        return lessonRepo.save(lesson);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        if (! existsById(id)) {
            throw new NotFoundException("Lesson ID", id.toString());
        }
        courseRepo.deleteById(id);
    }
}
