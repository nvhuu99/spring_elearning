package com.demo.app.admin.controllers;

import com.demo.models.Category;
import com.demo.services.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("admin")
public class AdminCategoryController extends AdminBaseController {
    private final ICategoryService categoryService;

    @GetMapping("categories")
    public String listCategories(
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        var records = categoryService.search(page, pageSize);
        var count = categoryService.countAll();
        var totalPages = (count + pageSize - 1) / pageSize;

        for(var cat : records) {
            cat.getCourses();
        }

        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalEntries", count);
        model.addAttribute("entries", records.size());
        model.addAttribute("categories", records);

        return "admin/category/list";
    }
}
