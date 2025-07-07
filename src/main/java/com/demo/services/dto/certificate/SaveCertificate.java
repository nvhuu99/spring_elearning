package com.demo.services.dto.certificate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveCertificate {
    @NotNull(message = "typ='NOT_NULL'; name='certificateName'")
    String certificateName;

    @NotNull(message = "typ='NOT_NULL'; name='issueDate'")
    @PastOrPresent(message = "typ='PAST_OR_PRESENT'; name='issueDate'")
    LocalDate issueDate;

    @NotNull(message = "typ='NOT_NULL'; name='userId'")
    Long userId;

    @NotEmpty(message = "typ='NOT_EMPTY'; name='courseIds'")
    List<Long> courseIds;
}