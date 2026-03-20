package com.btechhub.controller;

import com.btechhub.entity.SyllabusModule;
import com.btechhub.service.SyllabusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/syllabus")
@RequiredArgsConstructor
public class SyllabusController {

    private final SyllabusService syllabusService;

    @GetMapping
    public ResponseEntity<List<SyllabusModule>> getSyllabus(@RequestParam Long subjectId) {
        return ResponseEntity.ok(syllabusService.getBySubject(subjectId));
    }
}
