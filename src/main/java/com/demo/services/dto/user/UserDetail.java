package com.demo.services.dto.user;

import com.demo.models.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetail {
    Long id;
    String username;
    String email;
    LocalDate birthdate;
    String address;

    public static UserDetail fromEntity(User entity) {
        return new UserDetail(
            entity.getId(),
            entity.getUsername(),
            entity.getEmail(),
            entity.getBirthdate(),
            entity.getAddress()
        );
    }
}