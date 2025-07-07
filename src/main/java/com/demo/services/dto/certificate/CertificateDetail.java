package com.demo.services.dto.certificate;

import com.demo.models.Certificate;
import com.demo.models.Course;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class CertificateDetail {
    Long id;
    String certificateName;
    LocalDate issueDate;
    Long userId;
    String accoutName;
    List<CourseInfo> courseInfos;

    public static CertificateDetail fromEntity(Certificate entity) {
        var courseInfos = entity.getCourses()
            .stream()
            .map(c -> new CourseInfo(c.getId(), c.getTitle()))
            .toList();
        return new CertificateDetail(
            entity.getId(),
            entity.getCertificateName().getValue(),
            entity.getIssueDate(),
            entity.getUser().getId(),
            entity.getUser().getUsername(),
            courseInfos
        );
    }
}