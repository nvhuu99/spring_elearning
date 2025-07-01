package com.demo.api.controllers;

import com.demo.api.dto.LessonDTO;
import com.demo.api.responses.ApiResponse;
import com.demo.exceptions.NotFoundException;
import com.demo.services.ILessonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("lessons")
@AllArgsConstructor
public class LessonController {
    private final ILessonService lessonSvc;

    @GetMapping("{lessonId}")
    public ResponseEntity<?> getLesson(@PathVariable Long lessonId) throws Exception{
        var lesson = lessonSvc.findById(lessonId);
        if (lesson == null) {
            throw new NotFoundException("Lesson ID", lessonId.toString());
        }
        return ApiResponse.ok(lesson);
    }

    @PostMapping("{courseId}")
    public ResponseEntity<?> create(
            @PathVariable Long courseId,
            @Valid @RequestBody LessonDTO body
    ) throws Exception {
        return ApiResponse.created(
                LessonDTO.fromEntity(lessonSvc.save(body)));
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody LessonDTO body
    ) throws Exception {
        body.setId(id);
        return ApiResponse.ok(
                LessonDTO.fromEntity(lessonSvc.save(body)));
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        lessonSvc.deleteById(id);
        return ApiResponse.noContent();
    }
}
