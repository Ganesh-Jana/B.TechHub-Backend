package com.btechhub.controller;

import com.btechhub.entity.Material;
import com.btechhub.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping
    public ResponseEntity<List<Material>> getMaterials(@RequestParam Long subjectId) {
        return ResponseEntity.ok(materialService.getBySubject(subjectId));
    }

    @PostMapping
    public ResponseEntity<?> addMaterial(@RequestBody Map<String, Object> body) {
        try {
            return ResponseEntity.ok(materialService.add(body));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
