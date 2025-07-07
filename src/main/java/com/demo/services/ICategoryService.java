package com.demo.services;

import com.demo.services.dto.category.SaveCategory;
import com.demo.models.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAll();
    List<Category> search(Integer page, Integer size);
    Long countAll();
    Category findById(Long id);
    boolean existsById(Long id);
    Category save(Long id, SaveCategory cat) throws Exception;
    void deleteById(Long id) throws Exception;
}
