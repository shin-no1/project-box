package io.github.haeun.petstats.service.cache;

import io.github.haeun.petstats.domain.region.Region;
import io.github.haeun.petstats.domain.region.RegionRepository;
import io.github.haeun.petstats.domain.species.Species;
import io.github.haeun.petstats.domain.species.SpeciesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilterCacheService {
    private final RegionRepository regionRepository;
    private final SpeciesRepository speciesRepository;

    final int CACHE_EXPIRATION = 60 * 60 * 24;

    @Cacheable(value = "regions")
    public List<Region> getRegions() {
        log.info("[Cacheable] regions");
        return regionRepository.findAll();
    }

    @Scheduled(fixedDelay = CACHE_EXPIRATION)
    @CacheEvict(value = "regions", allEntries = true)
    public void evictRegions() {
        log.info("[CacheEvict] regions");
    }

}
