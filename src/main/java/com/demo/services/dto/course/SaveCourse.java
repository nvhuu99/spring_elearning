package com.demo.services.dto.course;

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
public class SaveCourse {
    @NotNull(message = "typ='NOT_NULL'; name='title'")
    @Length(min = 2, max = 100, message = "typ='LENGTH'; name='title'; min='2'; max='100'")
    String title;

    @NotNull(message = "typ='NOT_NULL'; name='days'")
    @Min(value = 0, message = "typ='MIN'; name='days'; min='0'")
    Integer days;

    @NotNull(message = "typ='NOT_NULL'; name='hours'")
    @Min(value = 0, message = "typ='MIN'; name='hours'; min='0'")
    Integer hours;

    @NotNull(message = "typ='NOT_NULL'; name='categoryId'")
    Long categoryId;
}
