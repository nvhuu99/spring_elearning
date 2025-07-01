package com.demo.api.dto;

import com.demo.models.Course;
import com.demo.models.Lesson;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class LessonDTO {
    Long id;

    @NotNull(message = "typ='NOT_NULL'; name='name'")
    @Length(min = 3, max = 200, message = "typ='LENGTH'; name='name'; min='3'; max='200'")
    String name;

    @Min(value = 0, message = "typ='MIN'; name='hours'; min='0'")
    Integer hours;

    @NotNull(message = "typ='NOT_NULL'; name='courseId'")
    Long courseId;

    public static LessonDTO fromEntity(Lesson entity) {
        return new LessonDTO(
                entity.getId(),
                entity.getName(),
                entity.getHours(),
                entity.getCourse().getId()
        );
    }
}
