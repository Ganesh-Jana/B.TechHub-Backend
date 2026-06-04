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

  @Value("${gemini.api.key}")
  private String geminiApiKey;

  private static final String SYSTEM_PROMPT = "You are BTechHub AI Assistant — a helpful study assistant for B.Tech engineering students. "
      +
      "Help with subject doubts across all engineering branches (CSE, IT, ECE, EE, ME, Civil) " +
      "for all 8 semesters. Keep answers clear, concise and student-friendly.";

  @PostMapping("/chat")
  public ResponseEntity<?> chat(@RequestBody Map<String, Object> body) {
    try {
      RestTemplate restTemplate = new RestTemplate();

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      // Build Gemini request
      List<Map<String, Object>> messages = (List<Map<String, Object>>) body.get("messages");

      // Convert messages to Gemini format
      List<Map<String, Object>> contents = new ArrayList<>();

      // Add system prompt as first user message
      Map<String, Object> systemContent = new HashMap<>();
      systemContent.put("role", "user");
      systemContent.put("parts", List.of(Map.of("text", SYSTEM_PROMPT)));
      contents.add(systemContent);

      // Add model acknowledgment
      Map<String, Object> modelAck = new HashMap<>();
      modelAck.put("role", "model");
      modelAck.put("parts", List.of(
          Map.of("text", "Understood! I'm BTechHub AI Assistant. I'll help B.Tech students with their study doubts.")));
      contents.add(modelAck);

      // Add actual conversation messages
      for (Map<String, Object> msg : messages) {
        Map<String, Object> content = new HashMap<>();
        String role = msg.get("role").toString();
        content.put("role", role.equals("assistant") ? "model" : "user");
        content.put("parts", List.of(Map.of("text", msg.get("content").toString())));
        contents.add(content);
      }

      Map<String, Object> requestBody = new HashMap<>();
      requestBody.put("contents", contents);
      requestBody.put("generationConfig", Map.of(
          "maxOutputTokens", 1000,
          "temperature", 0.7));

      String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key="
          + geminiApiKey;

      HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
      ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

      // Extract text from Gemini response
      Map responseBody = response.getBody();
      List candidates = (List) responseBody.get("candidates");
      Map firstCandidate = (Map) candidates.get(0);
      Map content = (Map) firstCandidate.get("content");
      List parts = (List) content.get("parts");
      Map firstPart = (Map) parts.get(0);
      String text = firstPart.get("text").toString();

      // Return in same format as before so frontend works without changes
      return ResponseEntity.ok(Map.of(
          "content", List.of(Map.of("type", "text", "text", text))));

    } catch (Exception e) {
      return ResponseEntity.status(500)
          .body(Map.of("error", "AI service error: " + e.getMessage()));
    }
  }
}