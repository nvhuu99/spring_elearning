package com.demo.app.api.controllers;

import com.demo.services.dto.category.CategoryDetail;
import com.demo.services.dto.category.SaveCategory;
import com.demo.exceptions.NotFoundException;
import com.demo.app.api.responses.ApiResponse;
import com.demo.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/categories")
@AllArgsConstructor
public class CategoryController {
    private final ICategoryService categorySvc;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        return ApiResponse.ok(
                categorySvc.getAll().stream()
                        .map(CategoryDetail::fromEntity)
                        .toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
        var cat = categorySvc.findById(id);
        if (cat == null) {
            throw new NotFoundException("Category ID", id.toString());
        }
        return ApiResponse.ok(CategoryDetail.fromEntity(cat));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SaveCategory body) throws Exception {
        return ApiResponse.created(
                CategoryDetail.fromEntity(categorySvc.save(null, body)));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(
        @PathVariable Long id,
        @Valid @RequestBody SaveCategory body
    ) throws Exception {
        return ApiResponse.ok(
                CategoryDetail.fromEntity(categorySvc.save(id, body)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        categorySvc.deleteById(id);
        return ApiResponse.noContent();
    }
}
