package io.github.haeun.petstats.service;

import io.github.haeun.petstats.domain.animalStats.AnimalStatsQueryRepository;
import io.github.haeun.petstats.web.dto.RegionTopAnimalTypeRequest;
import io.github.haeun.petstats.web.dto.RegionTopAnimalTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnimalTypeService {
    private final AnimalStatsQueryRepository animalStatsQueryRepository;

    public List<RegionTopAnimalTypeResponse> getTopAnimalTypes(RegionTopAnimalTypeRequest request) {
        return animalStatsQueryRepository.getTopAnimalTypeResponseList(request);
    }
}
