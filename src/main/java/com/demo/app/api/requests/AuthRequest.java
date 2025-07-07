package com.demo.app.api.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotNull(message = "typ='NOT_NULL'; name='username'")
    @Length(min = 2, max = 50, message = "typ='LENGTH'; name='username'; min='2'; max='50'")
    private String username;

    @NotNull(message = "typ='NOT_NULL'; name='password'")
    @Length(min = 3, max = 30, message = "typ='LENGTH'; name='password'; min='3'; max='30'")
    private String password;
}

