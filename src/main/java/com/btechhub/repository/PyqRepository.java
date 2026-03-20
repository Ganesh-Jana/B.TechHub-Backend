package com.btechhub.repository;

import com.btechhub.entity.Pyq;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PyqRepository extends JpaRepository<Pyq, Long> {
    List<Pyq> findBySubjectIdOrderByYearDesc(Long subjectId);
}
