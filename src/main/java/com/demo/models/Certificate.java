package com.demo.models;

import com.demo.models.dataconvert.CertificateNameConverter;
import com.demo.models.enums.CertificateName;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "certificates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Convert(converter = CertificateNameConverter.class)
    @Column(name = "certificate_name", columnDefinition = "ENUM('Associate', 'Professional', 'Master')")
    CertificateName certificateName;

    @Column(name = "issue_date", nullable = false)
    LocalDate issueDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    User user;

    @ManyToMany
    @JoinTable(
        name = "certificate_courses",
        joinColumns = @JoinColumn(name = "certificate_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"certificate_id", "course_id"})
    )
    @JsonManagedReference
    List<Course> courses = new ArrayList<>();
}