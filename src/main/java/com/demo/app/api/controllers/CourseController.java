package com.demo.app.api.controllers;

import com.demo.services.dto.course.CourseDetail;
import com.demo.services.dto.course.SaveCourse;
import com.demo.app.api.responses.ApiResponse;
import com.demo.exceptions.NotFoundException;
import com.demo.services.ICourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/courses")
@AllArgsConstructor
public class CourseController {
    private final ICourseService courseSvc;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        return ApiResponse.ok(
                courseSvc.getAll()
                        .stream()
                        .map(CourseDetail::fromEntity)
                        .toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
        var cat = courseSvc.findById(id);
        if (cat == null) {
            throw new NotFoundException("Course ID", id.toString());
        }
        return ApiResponse.ok(CourseDetail.fromEntity(cat));
    }

    @GetMapping("search")
    public ResponseEntity<?> search(@RequestParam String keyword) {
        return ApiResponse.ok(
                courseSvc.findByTitle(keyword)
                        .stream()
                        .map(CourseDetail::fromEntity)
                        .toList());
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<?> search(@PathVariable Long categoryId) {
        return ApiResponse.ok(
                courseSvc.findByCategoryId(categoryId)
                        .stream()
                        .map(CourseDetail::fromEntity)
                        .toList());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SaveCourse body) throws Exception {
        return ApiResponse.created(
                CourseDetail.fromEntity(courseSvc.save(null, body)));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(
        @PathVariable Long id,
        @Valid @RequestBody SaveCourse body
    ) throws Exception {
        return ApiResponse.ok(
                CourseDetail.fromEntity(courseSvc.save(id, body)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        courseSvc.deleteById(id);
        return ApiResponse.noContent();
    }
}
