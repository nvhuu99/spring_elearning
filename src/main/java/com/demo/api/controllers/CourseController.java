package com.demo.api.controllers;

import com.demo.api.dto.CourseDTO;
import com.demo.api.responses.ApiResponse;
import com.demo.exceptions.NotFoundException;
import com.demo.services.ICourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {
    private final ICourseService courseSvc;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        return ApiResponse.ok(
                courseSvc.getAll()
                        .stream()
                        .map(CourseDTO::fromEntity)
                        .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
        var cat = courseSvc.findById(id);
        if (cat == null) {
            throw new NotFoundException("Course ID", id.toString());
        }
        return ApiResponse.ok(CourseDTO.fromEntity(cat));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String keyword) {
        return ApiResponse.ok(
                courseSvc.findByTitle(keyword)
                        .stream()
                        .map(CourseDTO::fromEntity)
                        .toList());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> search(@PathVariable Long categoryId) {
        return ApiResponse.ok(
                courseSvc.findByCategoryId(categoryId)
                        .stream()
                        .map(CourseDTO::fromEntity)
                        .toList());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CourseDTO body) throws Exception {
        return ApiResponse.created(
                CourseDTO.fromEntity(courseSvc.save(body)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
        @PathVariable Long id,
        @Valid @RequestBody CourseDTO body
    ) throws Exception {
        body.setId(id);
        return ApiResponse.ok(
                CourseDTO.fromEntity(courseSvc.save(body)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        courseSvc.deleteById(id);
        return ApiResponse.noContent();
    }
}
