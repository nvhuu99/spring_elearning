package com.demo.services.drivers;

import com.demo.api.dto.CategoryDTO;
import com.demo.exceptions.NotFoundException;
import com.demo.models.Category;
import com.demo.repositories.ICategoryRepository;
import com.demo.services.ICategoryService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CategoryService implements ICategoryService {
    ICategoryRepository repo;

    @Override
    public List<Category> getAll() {
        return repo.findAll();
    }

    @Override
    public Category findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    @Override
    public Category save(CategoryDTO data) throws Exception {
        Category category = new Category();
        if (data.getId() != null) {
            category = findById(data.getId());
            if (category == null) {
                throw new NotFoundException("Category ID",  data.getId().toString());
            }
        }
        category.setName(data.getName());

        return repo.save(category);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        if (! existsById(id)) {
            throw new NotFoundException("Category ID", id.toString());
        }
        repo.deleteById(id);
    }
}
