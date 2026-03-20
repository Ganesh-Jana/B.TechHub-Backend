package com.btechhub.repository;

import com.btechhub.entity.SyllabusModule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SyllabusModuleRepository extends JpaRepository<SyllabusModule, Long> {
    List<SyllabusModule> findBySubjectIdOrderByModuleNumberAsc(Long subjectId);
}
