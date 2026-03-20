package com.btechhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pyq")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pyq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer year;

    @Column(name = "exam_type")
    private String examType;

    @Column(name = "file_url")
    private String fileUrl;

    private Integer pages;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
