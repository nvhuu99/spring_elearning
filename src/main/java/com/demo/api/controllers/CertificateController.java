package com.demo.api.controllers;

import com.demo.api.dto.CertificateDTO;
import com.demo.api.responses.ApiResponse;
import com.demo.exceptions.NotFoundException;
import com.demo.services.ICertificateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("certificates")
@AllArgsConstructor
public class CertificateController {
    private final ICertificateService certSvc;

    @GetMapping
    public ResponseEntity<?> getCerts() {
        return ApiResponse.ok(certSvc.getAll()
                .stream()
                .map(CertificateDTO::fromEntity)
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
    public ResponseEntity<?> create(@Valid @RequestBody CertificateDTO body) throws Exception {
        return ApiResponse.created(
                CertificateDTO.fromEntity(certSvc.save(body)));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody CertificateDTO body
    ) throws Exception {
        body.setId(id);
        return ApiResponse.ok(
                CertificateDTO.fromEntity(certSvc.save(body)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        return ApiResponse.noContent();
    }
}
