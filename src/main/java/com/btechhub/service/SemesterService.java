package com.btechhub.service;

import com.btechhub.entity.Semester;
import com.btechhub.repository.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterService {

    private final SemesterRepository semesterRepository;

    public List<Semester> getAll() {
        return semesterRepository.findAllByOrderByNumberAsc();
    }
}
