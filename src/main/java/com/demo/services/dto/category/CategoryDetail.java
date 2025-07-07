package com.demo.services.dto.category;

import com.demo.models.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CategoryDetail {
    Long id;
    String name;

    public static CategoryDetail fromEntity(Category entity) {
        return new CategoryDetail(
            entity.getId(),
            entity.getName()
        );
    }
}
