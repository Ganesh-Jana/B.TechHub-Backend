package com.btechhub.service;

import com.btechhub.entity.Video;
import com.btechhub.entity.Subject;
import com.btechhub.repository.VideoRepository;
import com.btechhub.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final SubjectRepository subjectRepository;

    public List<Video> getBySubject(Long subjectId) {
        return videoRepository.findBySubjectId(subjectId);
    }

    public Video add(Map<String, Object> body) {
        Long subjectId = Long.valueOf(body.get("subjectId").toString());
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Video video = Video.builder()
                .title(body.get("title").toString())
                .youtubeId(body.get("youtubeId").toString())
                .channel(body.getOrDefault("channel", "").toString())
                .duration(body.getOrDefault("duration", "").toString())
                .views(body.getOrDefault("views", "").toString())
                .subject(subject)
                .build();

        return videoRepository.save(video);
    }
}
