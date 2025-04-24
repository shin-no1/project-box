package io.github.haeun.petstats.web;

import io.github.haeun.petstats.service.AnimalTypeService;
import io.github.haeun.petstats.web.dto.RegionTopAnimalTypeRequest;
import io.github.haeun.petstats.web.dto.RegionTopAnimalTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AnimalTypeController {
    private final AnimalTypeService animalTypeService;

    @GetMapping("/api/top-animal-type")
    public List<RegionTopAnimalTypeResponse> getTopAnimalType(RegionTopAnimalTypeRequest regionTopAnimalTypeRequest) {
        return animalTypeService.getTopAnimalTypes(regionTopAnimalTypeRequest);
    }
}
