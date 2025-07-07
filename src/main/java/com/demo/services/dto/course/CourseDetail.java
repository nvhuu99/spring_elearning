package com.demo.services.dto.course;

import com.demo.models.Course;
import com.demo.services.dto.lesson.LessonDetail;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CourseDetail {
    Long id;
    String title;
    Integer days;
    Integer hours;
    Long categoryId;
    String categoryName;

    public static CourseDetail fromEntity(Course entity) {
        return new CourseDetail(
            entity.getId(),
            entity.getTitle(),
            entity.getDays(),
            entity.getHours(),
            entity.getCategory().getId(),
            entity.getCategory().getName()
        );
    }
}
