package com.btechhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "syllabus_modules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyllabusModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_number")
    private Integer moduleNumber;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String topics;           // stored as "topic1|topic2|topic3" in DB

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
