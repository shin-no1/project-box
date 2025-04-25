package io.github.haeun.petstats.service;

import io.github.haeun.petstats.domain.animalStats.AnimalStatsQueryRepository;
import io.github.haeun.petstats.domain.rfidType.RfidType;
import io.github.haeun.petstats.domain.species.Species;
import io.github.haeun.petstats.web.dto.RegionTopAnimalTypeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class PetStatsService {
    private final FilterService filterService;
    private final AnimalStatsQueryRepository animalStatsQueryRepository;

    public List<Map<String, Object>> getTopAnimalTypes(RegionTopAnimalTypeRequest request) {
        List<Map<String, Object>> response = new ArrayList<>();
        int count = 0;
        for (Species species : filterService.getSpecies()) {
            int finalCount = count;
            response.add(new HashMap<>() {{
                put("index", finalCount);
                put("name", species.getName());
                put("data", animalStatsQueryRepository.getTopBreedsBySpecies(request.getRegionId(), species.getId()));
            }});
            count++;
        }
        return response;
    }

    public List<Map<String, Object>> getTopRfidTypes(Integer birthYear) {
        List<Map<String, Object>> response = new ArrayList<>();
        int count = 0;
        for (RfidType rfidType : filterService.getRfidTypes()) {
            int finalCount = count;
            response.add(new HashMap<>() {{
                put("index", finalCount);
                put("name", rfidType.getName());
                put("data", animalStatsQueryRepository.getTopRfidType(birthYear, rfidType.getId()));
            }});
            count++;
        }
        return response;
    }
}
