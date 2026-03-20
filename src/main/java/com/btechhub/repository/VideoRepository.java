package com.btechhub.repository;

import com.btechhub.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findBySubjectId(Long subjectId);
}
