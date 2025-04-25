package io.github.haeun.petstats.web;

import io.github.haeun.petstats.service.PetStatsService;
import io.github.haeun.petstats.service.FilterService;
import io.github.haeun.petstats.web.dto.RegionTopAnimalTypeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class PetStatsController {
    private final PetStatsService petStatsService;
    private final FilterService filterService;

    @GetMapping("/animal-types/top")
    public String getTopAnimalType(Model model, RegionTopAnimalTypeRequest regionTopAnimalTypeRequest) {
        model.addAttribute("dataList", petStatsService.getTopAnimalTypes(regionTopAnimalTypeRequest));
        model.addAttribute("regions", filterService.getRegions(regionTopAnimalTypeRequest.getRegionId()));
        return "top-animal-types";
    }
}
