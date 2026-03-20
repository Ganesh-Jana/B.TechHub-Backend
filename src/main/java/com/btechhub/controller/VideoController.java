package com.btechhub.controller;

import com.btechhub.entity.Video;
import com.btechhub.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    public ResponseEntity<List<Video>> getVideos(@RequestParam Long subjectId) {
        return ResponseEntity.ok(videoService.getBySubject(subjectId));
    }

    @PostMapping
    public ResponseEntity<?> addVideo(@RequestBody Map<String, Object> body) {
        try {
            return ResponseEntity.ok(videoService.add(body));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
