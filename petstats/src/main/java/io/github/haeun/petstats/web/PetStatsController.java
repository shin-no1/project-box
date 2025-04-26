package io.github.haeun.petstats.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.haeun.petstats.service.PetStatsService;
import io.github.haeun.petstats.service.FilterService;
import io.github.haeun.petstats.web.dto.AnimalStatsTrendRequest;
import io.github.haeun.petstats.web.dto.AnimalStatsTrendResponse;
import io.github.haeun.petstats.web.dto.RegionTopAnimalTypeRequest;
import io.github.haeun.petstats.web.dto.TopRfidTypeRequest;
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

    @GetMapping("/intro")
    public String intro(Model model) {
        return "intro";
    }

    @GetMapping("/animal-types/top")
    public String getTopAnimalType(Model model, RegionTopAnimalTypeRequest request) {
        try {
            model.addAttribute("dataList", petStatsService.getTopAnimalTypes(request));
            model.addAttribute("regions", filterService.getRegions(request.getRegionId()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "top-animal-types";
    }

    @GetMapping("/rfid-types/top")
    public String getTopRfidType(Model model, TopRfidTypeRequest request) {
        try {
            List<Map<String, Object>> dataList = petStatsService.getTopRfidTypes(request);
            model.addAttribute("dataList", dataList);
            model.addAttribute("dataListJson", new ObjectMapper().writeValueAsString(dataList));
            model.addAttribute("birthYears", filterService.getBirthYears(request.getBirthYear()));
            model.addAttribute("isSelected", request.getBirthYear());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "top-rfid-types";
    }

    @GetMapping("/animal-stats/trend")
    public String getAnimalStatsTrend(Model model, AnimalStatsTrendRequest request) {
        try {
            List<AnimalStatsTrendResponse> dataList = petStatsService.getAnimalStatsTrend(request);
            model.addAttribute("dataList", dataList);
            model.addAttribute("dataListJson", new ObjectMapper().writeValueAsString(dataList));
            model.addAttribute("regions", filterService.getRegions(request.getRegionId()));
            model.addAttribute("isSelected", request.getRegionId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "animal-stats-trend";
    }
}
