package com.demo.api.dto;

import com.demo.models.Account;
import com.demo.models.Certificate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDTO {
    Long id;

    @NotNull(message = "typ='NOT_NULL'; name='username'")
    @Length(min = 2, max = 50, message = "typ='LENGTH'; name='username'; min='2'; max='50'")
    String username;

    @Email(message = "typ='EMAIL'; name='email'")
    @NotNull(message = "typ='NOT_NULL'; name='email'")
    @Length(min = 3, max = 100, message = "typ='LENGTH'; name='email'; min='3'; max='100'")
    String email;

    @NotNull(message = "typ='NOT_NULL'; name='password'")
    @Length(min = 3, max = 30, message = "typ='LENGTH'; name='password'; min='3'; max='30'")
    String password;

    @NotNull(message = "typ='NOT_NULL'; name='address'")
    String address;

    @NotNull(message = "typ='NOT_NULL'; name='birthdate'")
    @PastOrPresent(message = "typ='PAST_OR_PRESENT'; name='birthdate'")
    LocalDate birthdate;

    LocalDateTime created;

    LocalDateTime updated;

    public static AccountDTO fromEntity(Account entity) {
        return new AccountDTO(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getAddress(),
                entity.getBirthdate(),
                entity.getCreated(),
                entity.getUpdated()
        );
    }
}