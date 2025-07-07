package com.demo.services.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class SaveCategory {
    @NotNull(message = "typ='NOT_NULL'; name='name'")
    @Length(min = 3, max = 100, message = "typ='LENGTH'; name='name'; min='3'; max='100'")
    String name;
}
