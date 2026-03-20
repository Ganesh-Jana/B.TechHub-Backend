package com.btechhub.service;

import com.btechhub.entity.Material;
import com.btechhub.entity.Subject;
import com.btechhub.repository.MaterialRepository;
import com.btechhub.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final SubjectRepository subjectRepository;

    public List<Material> getBySubject(Long subjectId) {
        return materialRepository.findBySubjectId(subjectId);
    }

    public Material add(Map<String, Object> body) {
        Long subjectId = Long.valueOf(body.get("subjectId").toString());
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Material material = Material.builder()
                .title(body.get("title").toString())
                .fileUrl(body.get("fileUrl").toString())
                .type(body.getOrDefault("type", "PDF Notes").toString())
                .size(body.getOrDefault("size", "").toString())
                .uploadedBy(body.getOrDefault("uploadedBy", "Admin").toString())
                .uploadDate(LocalDate.now())
                .subject(subject)
                .build();

        return materialRepository.save(material);
    }
}
