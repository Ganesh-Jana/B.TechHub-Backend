package com.btechhub.controller;

import com.btechhub.entity.Pyq;
import com.btechhub.service.PyqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pyq")
@RequiredArgsConstructor
public class PyqController {

    private final PyqService pyqService;

    @GetMapping
    public ResponseEntity<List<Pyq>> getPYQ(@RequestParam Long subjectId) {
        return ResponseEntity.ok(pyqService.getBySubject(subjectId));
    }

    @PostMapping
    public ResponseEntity<?> addPYQ(@RequestBody Map<String, Object> body) {
        try {
            return ResponseEntity.ok(pyqService.add(body));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
