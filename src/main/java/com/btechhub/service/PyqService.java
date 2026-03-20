package com.btechhub.service;

import com.btechhub.entity.Pyq;
import com.btechhub.entity.Subject;
import com.btechhub.repository.PyqRepository;
import com.btechhub.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PyqService {

    private final PyqRepository pyqRepository;
    private final SubjectRepository subjectRepository;

    public List<Pyq> getBySubject(Long subjectId) {
        return pyqRepository.findBySubjectIdOrderByYearDesc(subjectId);
    }

    public Pyq add(Map<String, Object> body) {
        Long subjectId = Long.valueOf(body.get("subjectId").toString());
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Pyq pyq = Pyq.builder()
                .year(Integer.valueOf(body.get("year").toString()))
                .examType(body.getOrDefault("examType", "End Semester").toString())
                .fileUrl(body.get("fileUrl").toString())
                .pages(body.get("pages") != null ? Integer.valueOf(body.get("pages").toString()) : 0)
                .subject(subject)
                .build();

        return pyqRepository.save(pyq);
    }
}
