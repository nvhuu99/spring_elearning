package com.demo.services.drivers;

import com.demo.models.enums.CertificateName;
import com.demo.services.dto.certificate.SaveCertificate;
import com.demo.exceptions.NotFoundException;
import com.demo.models.Certificate;
import com.demo.models.Course;
import com.demo.repositories.ICertificateRepository;
import com.demo.services.IUserService;
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
    IUserService userSvc;

    @Override
    public List<Certificate> getAll() { return certRepo.findAll(); }

    @Override
    public Certificate findById(Long id) { return certRepo.findById(id).orElse(null); }

    @Override
    public boolean existsById(Long id) {
        return certRepo.existsById(id);
    }

    @Override
    public Certificate save(Long id, SaveCertificate data) throws Exception {
        // Get certificate
        var cert = new Certificate();
        if (id != null) {
            cert = findById(id);
            if (cert == null) {
                throw new NotFoundException("Certificate ID", id.toString());
            }
        }
        // Get user
        var user = userSvc.findById(data.getUserId());
        if (user == null) {
            throw new NotFoundException("User ID", data.getUserId().toString());
        }
        // Get courses
        var courses = new ArrayList<Course>();
        for (var cId : data.getCourseIds()) {
            var crs = courseSvc.findById(cId);
            if (crs == null) {
                throw new NotFoundException("Course ID", cId.toString());
            }
            courses.add(crs);
        }
        // Set data
        cert.setCertificateName(CertificateName.fromValue(data.getCertificateName()));
        cert.setIssueDate(data.getIssueDate());
        cert.setUser(user);
        if (id != null) {
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
