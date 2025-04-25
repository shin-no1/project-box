package io.github.haeun.petstats.web;

import io.github.haeun.petstats.domain.region.Region;
import io.github.haeun.petstats.service.AnimalTypeService;
import io.github.haeun.petstats.service.FilterService;
import io.github.haeun.petstats.web.dto.RegionResponse;
import io.github.haeun.petstats.web.dto.RegionTopAnimalTypeRequest;
import io.github.haeun.petstats.web.dto.RegionTopAnimalTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class AnimalTypeController {
    private final AnimalTypeService animalTypeService;
    private final FilterService filterService;

    @GetMapping("/animal-types/top")
    public String getTopAnimalType(Model model, RegionTopAnimalTypeRequest regionTopAnimalTypeRequest) {
        List<List<RegionTopAnimalTypeResponse>> topList = animalTypeService.getTopAnimalTypes(regionTopAnimalTypeRequest);
        model.addAttribute("dogList", topList.get(0));
        model.addAttribute("catList", topList.get(1));
        model.addAttribute("regions", filterService.getRegions(regionTopAnimalTypeRequest.getRegionId()));
        return "top-animal-types";
    }
}
