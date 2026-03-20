package com.btechhub.service;

import com.btechhub.entity.Semester;
import com.btechhub.entity.Stream;
import com.btechhub.entity.Subject;
import com.btechhub.repository.SemesterRepository;
import com.btechhub.repository.StreamRepository;
import com.btechhub.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;
    private final StreamRepository streamRepository;

    public List<Subject> getByFilter(Long semesterId, Long streamId) {
        if (semesterId != null && streamId != null) {
            return subjectRepository.findBySemesterIdAndStreamId(semesterId, streamId);
        } else if (semesterId != null) {
            return subjectRepository.findBySemesterId(semesterId);
        } else {
            return subjectRepository.findAll();
        }
    }

    public Subject add(Map<String, Object> body) {
        Long semesterId = Long.valueOf(body.get("semesterId").toString());
        Long streamId   = Long.valueOf(body.get("streamId").toString());

        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semester not found"));
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new RuntimeException("Stream not found"));

        Subject subject = Subject.builder()
                .name(body.get("name").toString())
                .subjectCode(body.get("subjectCode").toString())
                .credits(body.get("credits") != null ? Integer.valueOf(body.get("credits").toString()) : 3)
                .semester(semester)
                .stream(stream)
                .build();

        return subjectRepository.save(subject);
    }

    public void delete(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new RuntimeException("Subject not found");
        }
        subjectRepository.deleteById(id);
    }

    public Subject updateSyllabusPdf(Long id, String url) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        subject.setSyllabusPdfUrl(url);
        return subjectRepository.save(subject);
    }
}
