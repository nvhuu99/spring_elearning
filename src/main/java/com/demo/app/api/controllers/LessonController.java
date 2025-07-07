package com.demo.app.api.controllers;

import com.demo.app.api.responses.ApiResponse;
import com.demo.services.dto.lesson.LessonDetail;
import com.demo.services.dto.lesson.SaveLesson;
import com.demo.exceptions.NotFoundException;
import com.demo.services.ILessonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/lessons")
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
            @Valid @RequestBody SaveLesson body
    ) throws Exception {
        return ApiResponse.created(
                LessonDetail.fromEntity(lessonSvc.save(null, body)));
    }

    @PutMapping("{lessonId}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody SaveLesson body
    ) throws Exception {
        return ApiResponse.ok(
                LessonDetail.fromEntity(lessonSvc.save(id, body)));
    }

    @DeleteMapping("{lessonId}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        lessonSvc.deleteById(id);
        return ApiResponse.noContent();
    }
}
