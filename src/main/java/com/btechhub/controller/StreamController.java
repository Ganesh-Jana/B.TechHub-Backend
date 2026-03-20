package com.btechhub.controller;

import com.btechhub.entity.Stream;
import com.btechhub.service.StreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/streams")
@RequiredArgsConstructor
public class StreamController {

    private final StreamService streamService;

    @GetMapping
    public ResponseEntity<List<Stream>> getAll() {
        return ResponseEntity.ok(streamService.getAll());
    }
}
