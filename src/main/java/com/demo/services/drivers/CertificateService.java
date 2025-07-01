package com.demo.services.drivers;

import com.demo.api.dto.CertificateDTO;
import com.demo.exceptions.NotFoundException;
import com.demo.models.Certificate;
import com.demo.models.Course;
import com.demo.repositories.ICertificateRepository;
import com.demo.services.IAccountService;
import com.demo.services.ICertificateService;
import com.demo.services.ICourseService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class CertificateService implements ICertificateService {
    ICertificateRepository certRepo;
    ICourseService courseSvc;
    IAccountService accountSvc;

    @Override
    public List<Certificate> getAll() { return certRepo.findAll(); }

    @Override
    public Certificate findById(Long id) { return certRepo.findById(id).orElse(null); }

    @Override
    public boolean existsById(Long id) {
        return certRepo.existsById(id);
    }

    @Override
    public Certificate save(CertificateDTO data) throws Exception {
        // Get certificate
        var cert = new Certificate();
        if (data.getId() != null) {
            cert = findById(data.getId());
            if (cert == null) {
                throw new NotFoundException("Certificate ID", data.getId().toString());
            }
        }
        // Get account
        var account = accountSvc.findById(data.getAccountId());
        if (account == null) {
            throw new NotFoundException("Account ID", data.getId().toString());
        }
        // Get courses
        var courses = new ArrayList<Course>();
        for (var id : data.getCourseIds()) {
            var crs = courseSvc.findById(id);
            if (crs == null) {
                throw new NotFoundException("Course ID", id.toString());
            }
            courses.add(crs);
        }
        // Set data
        cert.setGrade(data.getGrade());
        cert.setIssueDate(data.getIssueDate());
        cert.setAccount(account);
        if (data.getId() != null) {
            // delete all certificate_courses records
            cert.getCourses().clear();
            certRepo.save(cert);
        }
        cert.setCourses(courses);
        return certRepo.save(cert);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        var certificate = findById(id);
        if (certificate == null) {
            throw new NotFoundException("Certificate ID", id.toString());
        }
        // delete all certificate_courses records
        certificate.getCourses().clear();
        certRepo.save(certificate);
        // delete certificate
        certRepo.deleteById(id);
    }
}
