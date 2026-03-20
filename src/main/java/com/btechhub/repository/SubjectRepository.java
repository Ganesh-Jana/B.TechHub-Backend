package com.btechhub.repository;

import com.btechhub.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT s FROM Subject s WHERE s.semester.id = :semesterId AND s.stream.id = :streamId")
    List<Subject> findBySemesterIdAndStreamId(@Param("semesterId") Long semesterId,
                                               @Param("streamId") Long streamId);

    @Query("SELECT s FROM Subject s WHERE s.semester.id = :semesterId")
    List<Subject> findBySemesterId(@Param("semesterId") Long semesterId);
}
