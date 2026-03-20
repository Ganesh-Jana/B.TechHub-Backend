package com.btechhub.service;

import com.btechhub.entity.SyllabusModule;
import com.btechhub.repository.SyllabusModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyllabusService {

    private final SyllabusModuleRepository syllabusModuleRepository;

    public List<SyllabusModule> getBySubject(Long subjectId) {
        return syllabusModuleRepository.findBySubjectIdOrderByModuleNumberAsc(subjectId);
    }
}
