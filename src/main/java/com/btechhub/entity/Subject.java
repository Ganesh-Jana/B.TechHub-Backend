package com.btechhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "subject_code", nullable = false)
    private String subjectCode;

    private Integer credits;

    @Column(name = "syllabus_pdf_url")
    private String syllabusPdfUrl;      // ← NEW: stores PDF link from Cloudinary

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id")
    @JsonIgnore
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stream_id")
    @JsonIgnore
    private Stream stream;

    // Expose IDs to frontend without loading full objects
    public Long getSemesterId() { return semester != null ? semester.getId() : null; }
    public Long getStreamId()   { return stream   != null ? stream.getId()   : null; }
}
