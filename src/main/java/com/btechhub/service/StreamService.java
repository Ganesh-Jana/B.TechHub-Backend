package com.btechhub.service;

import com.btechhub.entity.Stream;
import com.btechhub.repository.StreamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StreamService {

    private final StreamRepository streamRepository;

    public List<Stream> getAll() {
        return streamRepository.findAll();
    }
}
