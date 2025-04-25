package io.github.haeun.petstats.service;

import io.github.haeun.petstats.domain.region.Region;
import io.github.haeun.petstats.domain.rfidType.RfidType;
import io.github.haeun.petstats.domain.species.Species;
import io.github.haeun.petstats.service.cache.FilterCacheService;
import io.github.haeun.petstats.web.dto.RegionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class FilterService {
    private final FilterCacheService filterCacheService;

    public List<RegionResponse> getRegions(Integer regionId) {
        List<Region> regions = filterCacheService.getRegions();
        return regions.stream()
                .map(region -> new RegionResponse(
                        region.getId(),
                        region.getProvince(),
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
}
