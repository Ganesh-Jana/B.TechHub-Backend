package com.btechhub.controller;

import com.btechhub.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final MaterialRepository materialRepository;
    private final PyqRepository pyqRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(Map.of(
            "materials", materialRepository.count(),
            "pyq",       pyqRepository.count(),
            "videos",    videoRepository.count(),
            "users",     userRepository.count()
        ));
    }
}
