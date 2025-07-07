package com.demo.services.dto.lesson;

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
public class SaveLesson {
    @NotNull(message = "typ='NOT_NULL'; name='title'")
    @Length(min = 3, max = 200, message = "typ='LENGTH'; name='title'; min='3'; max='200'")
    String title;

    @NotNull(message = "typ='NOT_NULL'; name='hours'")
    @Min(value = 0, message = "typ='MIN'; name='hours'; min='0'")
    Integer hours;

    @NotNull(message = "typ='NOT_NULL'; name='courseId'")
    Long courseId;
}
