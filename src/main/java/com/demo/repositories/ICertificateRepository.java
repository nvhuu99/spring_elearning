package com.demo.repositories;

import com.demo.models.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICertificateRepository extends JpaRepository<Certificate, Long> {
}
