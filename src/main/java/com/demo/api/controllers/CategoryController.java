package com.demo.api.controllers;

import com.demo.api.dto.CategoryDTO;
import com.demo.exceptions.NotFoundException;
import com.demo.api.responses.ApiResponse;
import com.demo.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private final ICategoryService categorySvc;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        return ApiResponse.ok(
                categorySvc.getAll().stream()
                        .map(CategoryDTO::fromEntity)
                        .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
        var cat = categorySvc.findById(id);
        if (cat == null) {
            throw new NotFoundException("Category ID", id.toString());
        }
        return ApiResponse.ok(CategoryDTO.fromEntity(cat));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CategoryDTO body) throws Exception {
        return ApiResponse.created(
                CategoryDTO.fromEntity(categorySvc.save(body)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
        @PathVariable Long id,
        @Valid @RequestBody CategoryDTO body
    ) throws Exception {
        body.setId(id);
        return ApiResponse.ok(
                CategoryDTO.fromEntity(categorySvc.save(body)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        categorySvc.deleteById(id);
        return ApiResponse.noContent();
    }
}
