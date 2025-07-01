package com.demo.api.dto;

import com.demo.models.Category;
import com.demo.models.Course;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CourseDTO {
    Long id;

    @NotNull(message = "typ='NOT_NULL'; name='title'")
    @Length(min = 2, max = 100, message = "typ='LENGTH'; name='title'; min='2'; max='100'")
    String title;

    @Min(value = 0, message = "typ='MIN'; name='days'; min='0'")
    Integer days;

    @Min(value = 0, message = "typ='MIN'; name='hours'; min='0'")
    Integer hours;

    @NotNull(message = "typ='NOT_NULL'; name='categoryId'")
    Long categoryId;

    public static CourseDTO fromEntity(Course entity) {
        return new CourseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDays(),
                entity.getHours(),
                entity.getCategory().getId()
        );
    }
}
