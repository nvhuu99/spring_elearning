package com.demo.services.drivers;

import com.demo.services.dto.category.SaveCategory;
import com.demo.exceptions.NotFoundException;
import com.demo.models.Category;
import com.demo.repositories.ICategoryRepository;
import com.demo.services.ICategoryService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CategoryService implements ICategoryService {
    final ICategoryRepository repo;


    @Override
    public List<Category> getAll() { return repo.findAll(); }

    @Override
    public List<Category> search(Integer page, Integer size) {
        var result = repo.findAll(PageRequest.of(page, size));
        return result.getContent();
    }

    @Override
    public Long countAll() { return repo.count(); }

    @Override
    public Category findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    @Override
    public Category save(Long id, SaveCategory data) throws Exception {
        Category category = new Category();
        if (id != null) {
            category = findById(id);
            if (category == null) {
                throw new NotFoundException("Category ID", id.toString());
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
