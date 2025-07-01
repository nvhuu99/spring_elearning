package com.demo.api.dto;

import com.demo.models.Category;
import com.demo.models.Course;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CategoryDTO {
    Long id;

    @NotNull(message = "typ='NOT_NULL'; name='name'")
    @Length(min = 3, max = 100, message = "typ='LENGTH'; name='name'; min='3'; max='100'")
    String name;

    public static CategoryDTO fromEntity(Category entity) {
        return new CategoryDTO(
                entity.getId(),
                entity.getName()
        );
    }
}
