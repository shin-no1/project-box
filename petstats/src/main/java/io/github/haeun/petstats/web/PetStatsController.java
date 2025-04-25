package io.github.haeun.petstats.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.haeun.petstats.service.PetStatsService;
import io.github.haeun.petstats.service.FilterService;
import io.github.haeun.petstats.web.dto.RegionTopAnimalTypeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PetStatsController {
    private final PetStatsService petStatsService;
    private final FilterService filterService;

    @GetMapping("/animal-types/top")
    public String getTopAnimalType(Model model, RegionTopAnimalTypeRequest regionTopAnimalTypeRequest) {
        try {
            model.addAttribute("dataList", petStatsService.getTopAnimalTypes(regionTopAnimalTypeRequest));
            model.addAttribute("regions", filterService.getRegions(regionTopAnimalTypeRequest.getRegionId()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "top-animal-types";
    }

    @GetMapping("/rfid-types/top")
    public String getTopRfidType(Model model) {
        try {
            List<Map<String, Object>> dataList = petStatsService.getTopRfidTypes();
            model.addAttribute("dataList", dataList);
            model.addAttribute("dataListJson", new ObjectMapper().writeValueAsString(dataList));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "top-rfid-types";
    }
}
