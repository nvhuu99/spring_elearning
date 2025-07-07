package com.demo.app.api.controllers;

import com.demo.services.dto.certificate.CertificateDetail;
import com.demo.services.dto.certificate.SaveCertificate;
import com.demo.app.api.responses.ApiResponse;
import com.demo.exceptions.NotFoundException;
import com.demo.services.ICertificateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/certificates")
@AllArgsConstructor
public class CertificateController {
    private final ICertificateService certSvc;

    @GetMapping
    public ResponseEntity<?> getCerts() {
        return ApiResponse.ok(certSvc.getAll()
                .stream()
                .map(CertificateDetail::fromEntity)
                .toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCertById(@PathVariable Long id) throws Exception {
        var cert = certSvc.findById(id);
        if (cert == null) {
            throw new NotFoundException("Certificate ID", id.toString());
        }
        return ApiResponse.ok(cert);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SaveCertificate body) throws Exception {
        return ApiResponse.created(
                CertificateDetail.fromEntity(certSvc.save(null, body)));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody SaveCertificate body
    ) throws Exception {
        return ApiResponse.ok(
                CertificateDetail.fromEntity(certSvc.save(id, body)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        return ApiResponse.noContent();
    }
}
