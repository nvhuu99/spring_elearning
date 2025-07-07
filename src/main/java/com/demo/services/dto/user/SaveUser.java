package com.demo.services.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveUser {
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

    @PastOrPresent(message = "typ='PAST_OR_PRESENT'; name='birthdate'")
    LocalDate birthdate;

    String address;
}