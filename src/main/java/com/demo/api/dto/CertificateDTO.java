package com.demo.api.dto;

import com.demo.models.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class CertificateDTO {
    Long id;

    @NotNull(message = "typ='NOT_NULL'; name='grade'")
    String grade;

    @NotNull(message = "typ='NOT_NULL'; name='issueDate'")
    @PastOrPresent(message = "typ='PAST_OR_PRESENT'; name='issueDate'")
    LocalDate issueDate;

    @NotNull(message = "typ='NOT_NULL'; name='accountId'")
    Long accountId;

    @NotEmpty(message = "typ='NOT_EMPTY'; name='courseIds'")
    List<Long> courseIds;

    public static CertificateDTO fromEntity(Certificate entity) {
        return new CertificateDTO(
                entity.getId(),
                entity.getGrade().toString(),
                entity.getIssueDate(),
                entity.getAccount().getId(),
                entity.getCourses().stream().map(Course::getId).toList()
        );
    }
}