package io.github.haeun.petstats.service;

import io.github.haeun.petstats.config.QuerydslConfiguration;
import io.github.haeun.petstats.domain.region.Region;
import io.github.haeun.petstats.domain.rfidType.RfidType;
import io.github.haeun.petstats.domain.species.Species;
import io.github.haeun.petstats.service.cache.FilterCacheService;
import io.github.haeun.petstats.service.cache.QueryFilterCacheService;
import io.github.haeun.petstats.web.dto.BirthYearResponse;
import io.github.haeun.petstats.web.dto.RegionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class FilterService {
    private final FilterCacheService filterCacheService;
    private final QueryFilterCacheService queryFilterCacheService;

    public List<RegionResponse> getRegions(Integer regionId) {
        List<Region> regions = filterCacheService.getRegions();
        return regions.stream()
                .map(region -> new RegionResponse(
                        region.getId(),
                        region.getCity(),
                        Objects.equals(region.getId(), regionId)
                ))
                .toList();
    }

    public List<Species> getSpecies() {
        return filterCacheService.getSpecies();
    }

    public List<RfidType> getRfidTypes() {
        return filterCacheService.getRfidTypes();
    }

    public List<BirthYearResponse> getBirthYears(Integer selectedBirthYear) {
        return queryFilterCacheService.getBirthYears().stream()
                .map(birthYear -> new BirthYearResponse(
                        birthYear,
                        Objects.equals(birthYear, selectedBirthYear)
                ))
                .toList();
    }
}
