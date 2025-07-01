package com.demo.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, length = 50)
    String username;

    @Column(nullable = false, unique = true, length = 100)
    String email;

    @Column(nullable = false, length = 30)
    String password;

    @Column(nullable = false, columnDefinition = "TEXT")
    String address;

    @Column(nullable = false)
    LocalDate birthdate;

    @Column(nullable = false, updatable = false)
    LocalDateTime created;

    @Column(nullable = false)
    LocalDateTime updated;

    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    List<Certificate> certificates;
}