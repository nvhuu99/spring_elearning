package com.demo.services.dto.lesson;

import com.demo.models.Lesson;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class LessonDetail {
    Long id;
    String title;
    Integer hours;
    Long courseId;
    String courseName;

    public static LessonDetail fromEntity(Lesson entity) {
        return new LessonDetail(
            entity.getId(),
            entity.getTitle(),
            entity.getHours(),
            entity.getCourse().getId(),
            entity.getCourse().getTitle()
        );
    }
}
