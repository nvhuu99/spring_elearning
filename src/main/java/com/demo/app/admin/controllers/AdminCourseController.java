package com.demo.app.admin.controllers;

import com.demo.services.ICategoryService;
import com.demo.services.ICourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("admin")
public class AdminCourseController extends AdminBaseController {
    private final ICourseService courseService;
    private final ICategoryService categoryService;

    @GetMapping("courses")
    public String listCourses(
            @RequestParam(defaultValue = "") String searchTerm,
            @RequestParam(defaultValue = "") Long catId,
            @RequestParam(defaultValue = "0") Integer page,
            Model model
    ) {
        var courses = courseService.searchCourses(catId, searchTerm, page, pageSize);
        var categories = categoryService.getAll();
        var count = courseService.countCourses(catId, searchTerm);
        var totalPages = (count + pageSize - 1) / pageSize;

        for(var course : courses) {
            course.getCategory();
            course.getLessons();
        }

        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("catId", catId);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalEntries", count);
        model.addAttribute("entries", courses.size());
        model.addAttribute("courses", courses);
        model.addAttribute("categories", categories);

        return "admin/course/list";
    }
}
