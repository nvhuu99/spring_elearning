package com.demo.services;

import com.demo.api.dto.CategoryDTO;
import com.demo.models.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAll();
    Category findById(Long id);
    boolean existsById(Long id);
    Category save(CategoryDTO cat) throws Exception;
    void deleteById(Long id) throws Exception;
}
