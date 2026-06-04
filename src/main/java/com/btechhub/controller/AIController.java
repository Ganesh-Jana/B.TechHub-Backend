package com.btechhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {
  @Value("${anthropic.api.key}")
  private String anthropicApiKey;

  @PostMapping("/chat")
  public ResponseEntity<?> chat(@RequestBody Map<String, Object> body) {
    try {
      RestTemplate restTemplate = new RestTemplate();

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.set("x-api-key", anthropicApiKey);
      headers.set("anthropic-version", "2023-06-01");

      Map<String, Object> requestBody = new HashMap<>();
      requestBody.put("model", "claude-haiku-4-5-20251001");
      requestBody.put("max_tokens", 1000);
      requestBody.put("system",
          "You are BTechHub AI Assistant — a helpful study assistant for B.Tech engineering students. Help with subject doubts across all engineering branches (CSE, IT, ECE, EE, ME, Civil) for all 8 semesters. Keep answers clear, concise and student-friendly.");
      requestBody.put("messages", body.get("messages"));

      HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

      ResponseEntity<Map> response = restTemplate.postForEntity(
          "https://api.anthropic.com/v1/messages",
          request,
          Map.class);

      return ResponseEntity.ok(response.getBody());

    } catch (Exception e) {
      return ResponseEntity.status(500)
          .body(Map.of("error", "AI service error: " + e.getMessage()));
    }
  }
}
