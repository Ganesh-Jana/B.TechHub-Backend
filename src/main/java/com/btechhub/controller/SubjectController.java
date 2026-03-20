package com.btechhub.controller;

import com.btechhub.entity.Subject;
import com.btechhub.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<Subject>> getSubjects(
            @RequestParam(required = false) Long semesterId,
            @RequestParam(required = false) Long streamId) {
        return ResponseEntity.ok(subjectService.getByFilter(semesterId, streamId));
    }

    @PostMapping
    public ResponseEntity<?> addSubject(@RequestBody Map<String, Object> body) {
        try {
            return ResponseEntity.ok(subjectService.add(body));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
        try {
            subjectService.delete(id);
            return ResponseEntity.ok(Map.of("message", "Subject deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/syllabus-pdf")
    public ResponseEntity<?> updateSyllabusPdf(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        try {
            String url = body.get("syllabusPdfUrl").toString();
            return ResponseEntity.ok(subjectService.updateSyllabusPdf(id, url));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
