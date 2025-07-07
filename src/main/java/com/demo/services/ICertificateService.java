package com.demo.services;

import com.demo.services.dto.certificate.SaveCertificate;
import com.demo.models.Certificate;

import java.util.List;

public interface ICertificateService {
    List<Certificate> getAll();
    Certificate findById(Long id);
    boolean existsById(Long id);
    Certificate save(Long id, SaveCertificate data) throws Exception;
    void deleteById(Long id) throws Exception;
}
